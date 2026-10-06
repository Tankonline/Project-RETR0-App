import express from "express";
import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StreamableHTTPServerTransport } from "@modelcontextprotocol/sdk/server/streamableHttp.js";
import { z } from "zod";

const PORT = Number(process.env.PORT || 8787);
const ACCOUNT_ID = "7dab9267e4f0a841968600a81e8f638f";
const API_BASE = "https://api.cloudflare.com/client/v4";

const token = process.env.CLOUDFLARE_API_TOKEN;

if (!token) {
    console.error("CLOUDFLARE_API_TOKEN is not set.");
    process.exit(1);
}

async function cloudflareGet(path) {
    if (!path.startsWith("/")) {
        throw new Error("Cloudflare API path must start with /");
    }

    // This server is intentionally GET-only.
    // No POST, PUT, PATCH or DELETE Cloudflare API calls are implemented.
    const response = await fetch(`${API_BASE}${path}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Accept": "application/json"
        }
    });

    const text = await response.text();

    if (!response.ok) {
        throw new Error(`Cloudflare API ${response.status}: ${text}`);
    }

    return text;
}

async function downloadWorker(scriptName) {
    const response = await fetch(
        `${API_BASE}/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(scriptName)}`,
        {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {
        const text = await response.text();
        throw new Error(`Cloudflare API ${response.status}: ${text}`);
    }

    return await response.text();
}

function createServer() {
    const server = new McpServer({
        name: "RETR0 Cloudflare Read Only",
        version: "1.0.0"
    });

    server.registerTool(
        "cloudflare_list_workers",
        {
            description: "List all Cloudflare Workers in the configured account.",
            inputSchema: {}
        },
        async () => {
            const result = await cloudflareGet(
                `/accounts/${ACCOUNT_ID}/workers/scripts`
            );

            return {
                content: [
                    {
                        type: "text",
                        text: result
                    }
                ]
            };
        }
    );

    server.registerTool(
        "cloudflare_list_worker_versions",
        {
            description: "List versions of a Cloudflare Worker.",
            inputSchema: {
                worker: z.string().min(1)
            }
        },
        async ({ worker }) => {
            const result = await cloudflareGet(
                `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(worker)}/versions`
            );

            return {
                content: [
                    {
                        type: "text",
                        text: result
                    }
                ]
            };
        }
    );

    server.registerTool(
        "cloudflare_get_worker_version",
        {
            description: "Retrieve metadata and resources for a specific Cloudflare Worker version.",
            inputSchema: {
                worker: z.string().min(1),
                versionId: z.string().min(1)
            }
        },
        async ({ worker, versionId }) => {
            const result = await cloudflareGet(
                `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(worker)}/versions/${encodeURIComponent(versionId)}`
            );

            return {
                content: [
                    {
                        type: "text",
                        text: result
                    }
                ]
            };
        }
    );

    server.registerTool(
        "cloudflare_download_worker",
        {
            description: "Download the currently deployed raw Worker script. This operation is read-only.",
            inputSchema: {
                worker: z.string().min(1)
            }
        },
        async ({ worker }) => {
            const result = await downloadWorker(worker);

            return {
                content: [
                    {
                        type: "text",
                        text: result
                    }
                ]
            };
        }
    );

    server.registerTool(
        "cloudflare_get_worker_settings",
        {
            description: "Read Worker script settings. This operation is read-only.",
            inputSchema: {
                worker: z.string().min(1)
            }
        },
        async ({ worker }) => {
            const result = await cloudflareGet(
                `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(worker)}/script-settings`
            );

            return {
                content: [
                    {
                        type: "text",
                        text: result
                    }
                ]
            };
        }
    );

    server.registerTool(
        "cloudflare_list_worker_deployments",
        {
            description: "List deployments of a Cloudflare Worker.",
            inputSchema: {
                worker: z.string().min(1)
            }
        },
        async ({ worker }) => {
            const result = await cloudflareGet(
                `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(worker)}/deployments`
            );

            return {
                content: [
                    {
                        type: "text",
                        text: result
                    }
                ]
            };
        }
    );

    return server;
}

const app = express();

app.get("/health", (_req, res) => {
    res.json({
        status: "ok",
        server: "RETR0 Cloudflare Read Only MCP"
    });
});

app.post("/mcp", async (req, res) => {
    try {
        const server = createServer();

        const transport = new StreamableHTTPServerTransport({
            sessionIdGenerator: undefined,
            enableJsonResponse: true
        });

        await server.connect(transport);

        await transport.handleRequest(req, res);
    } catch (error) {
        console.error(error);

        if (!res.headersSent) {
            res.status(500).json({
                error: "MCP server error"
            });
        }
    }
});

app.listen(PORT, "127.0.0.1", () => {
    console.log(`RETR0 Cloudflare MCP running at http://127.0.0.1:${PORT}/mcp`);
    console.log(`Health check: http://127.0.0.1:${PORT}/health`);
    console.log("Cloudflare access: READ ONLY");
});
