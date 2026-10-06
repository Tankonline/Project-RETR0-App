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

/*
 * ================================================================
 * PROJECT RETR0 CLOUDFLARE MCP
 * VERSION 3.0.0
 * ================================================================
 *
 * FULL CLOUDFLARE API ACCESS
 *
 * This MCP intentionally exposes the Cloudflare REST API to the
 * connected AI agent.
 *
 * Supported HTTP methods:
 *
 * GET
 * POST
 * PUT
 * PATCH
 * DELETE
 *
 * This means the connected AI can potentially:
 *
 * - deploy Workers
 * - upload Worker source
 * - modify Worker settings
 * - create/delete Worker deployments
 * - create/delete Worker secrets
 * - modify D1
 * - modify R2
 * - modify KV
 * - modify DNS
 * - modify zones
 * - modify security settings
 * - manage routes
 * - manage other Cloudflare products
 *
 * The actual permissions are determined by CLOUDFLARE_API_TOKEN.
 *
 * NEVER put the token into this source file.
 *
 * ================================================================
 */

function validatePath(path) {
    if (!path || typeof path !== "string") {
        throw new Error("Cloudflare API path is required.");
    }

    if (!path.startsWith("/")) {
        throw new Error(
            "Cloudflare API path must begin with '/'."
        );
    }

    if (path.includes("\0")) {
        throw new Error(
            "Invalid Cloudflare API path."
        );
    }

    /*
     * Only allow Cloudflare API resources.
     */
    if (
        !path.startsWith("/accounts/") &&
        !path.startsWith("/zones/") &&
        !path.startsWith("/user/")
    ) {
        throw new Error(
            "Cloudflare API paths must begin with /accounts/, /zones/, or /user/."
        );
    }
}

function buildUrl(path) {
    validatePath(path);

    return `${API_BASE}${path}`;
}

function normalizeBody(body) {
    if (body === undefined || body === null) {
        return undefined;
    }

    if (typeof body === "string") {
        return body;
    }

    return JSON.stringify(body);
}

/*
 * Generic Cloudflare REST request.
 *
 * This is intentionally the central write/read interface.
 */
async function cloudflareRequest({
    method,
    path,
    body,
    contentType = "application/json",
}) {
    const url = buildUrl(path);

    const normalizedMethod =
        String(method).toUpperCase();

    const allowedMethods = [
        "GET",
        "POST",
        "PUT",
        "PATCH",
        "DELETE",
    ];

    if (!allowedMethods.includes(normalizedMethod)) {
        throw new Error(
            `Unsupported HTTP method: ${normalizedMethod}`
        );
    }

    const requestBody =
        normalizedMethod === "GET"
            ? undefined
            : normalizeBody(body);

    const headers = {
        Authorization: `Bearer ${token}`,
        Accept: "application/json",
    };

    if (requestBody !== undefined) {
        headers["Content-Type"] = contentType;
    }

    const response = await fetch(url, {
        method: normalizedMethod,
        headers,
        body: requestBody,
    });

    const responseContentType =
        response.headers.get("content-type") ||
        "application/octet-stream";

    const raw = await response.arrayBuffer();

    const bytes = new Uint8Array(raw);

    let result;

    if (
        responseContentType.includes(
            "application/json"
        ) ||
        responseContentType.includes(
            "+json"
        )
    ) {
        const text =
            new TextDecoder().decode(bytes);

        try {
            result = JSON.parse(text);
        } catch {
            result = text;
        }
    } else if (
        responseContentType.startsWith(
            "text/"
        )
    ) {
        result =
            new TextDecoder().decode(bytes);
    } else {
        result = {
            encoding: "base64",
            data:
                Buffer.from(bytes).toString(
                    "base64"
                ),
        };
    }

    if (!response.ok) {
        throw new Error(
            `Cloudflare API ${response.status}: ${typeof result === "string"
                ? result
                : JSON.stringify(result)}`
        );
    }

    return {
        status: response.status,
        ok: response.ok,
        contentType: responseContentType,
        result,
    };
}

function jsonResult(value) {
    return {
        content: [
            {
                type: "text",
                text:
                    typeof value === "string"
                        ? value
                        : JSON.stringify(
                              value,
                              null,
                              2
                          ),
            },
        ],
    };
}

function createServer() {
    const server = new McpServer({
        name: "RETR0 Cloudflare Full Access",
        version: "3.0.0",
    });

    // ================================================================
    // GENERAL CLOUDFLARE API
    // ================================================================

    server.registerTool(
        "cloudflare_api_request",
        {
            description:
                "Perform an authenticated Cloudflare REST API request. Supports GET, POST, PUT, PATCH and DELETE against /accounts/, /zones/ and /user/ resources. Use this for Cloudflare products not covered by dedicated tools.",
            inputSchema: {
                method: z.enum([
                    "GET",
                    "POST",
                    "PUT",
                    "PATCH",
                    "DELETE",
                ]),

                path: z
                    .string()
                    .min(2)
                    .describe(
                        "Cloudflare API path beginning with /accounts/, /zones/, or /user/."
                    ),

                body: z
                    .any()
                    .optional()
                    .describe(
                        "JSON request body for POST, PUT or PATCH. DELETE bodies are supported if the Cloudflare endpoint requires one."
                    ),

                contentType: z
                    .string()
                    .optional()
                    .default(
                        "application/json"
                    ),
            },
        },
        async ({
            method,
            path,
            body,
            contentType,
        }) =>
            jsonResult(
                await cloudflareRequest({
                    method,
                    path,
                    body,
                    contentType,
                })
            )
    );

    // ================================================================
    // ACCOUNT
    // ================================================================

    server.registerTool(
        "cloudflare_get_account",
        {
            description:
                "Read the configured Cloudflare account.",
            inputSchema: {},
        },
        async () =>
            jsonResult(
                await cloudflareRequest({
                    method: "GET",
                    path: `/accounts/${ACCOUNT_ID}`,
                })
            )
    );

    server.registerTool(
        "cloudflare_account_request",
        {
            description:
                "Perform a Cloudflare API request against the configured account.",
            inputSchema: {
                method: z.enum([
                    "GET",
                    "POST",
                    "PUT",
                    "PATCH",
                    "DELETE",
                ]),
                path: z
                    .string()
                    .min(1)
                    .describe(
                        "Path after /accounts/<account-id>."
                    ),
                body: z.any().optional(),
            },
        },
        async ({
            method,
            path,
            body,
        }) => {
            const cleanPath =
                path.startsWith("/")
                    ? path
                    : `/${path}`;

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path: `/accounts/${ACCOUNT_ID}${cleanPath}`,
                    body,
                })
            );
        }
    );

    // ================================================================
    // WORKERS
    // ================================================================

    server.registerTool(
        "cloudflare_list_workers",
        {
            description:
                "List Cloudflare Workers.",
            inputSchema: {},
        },
        async () =>
            jsonResult(
                await cloudflareRequest({
                    method: "GET",
                    path: `/accounts/${ACCOUNT_ID}/workers/scripts`,
                })
            )
    );

    server.registerTool(
        "cloudflare_worker_request",
        {
            description:
                "Perform any Cloudflare API operation against a specific Worker.",
            inputSchema: {
                worker: z.string().min(1),
                method: z.enum([
                    "GET",
                    "POST",
                    "PUT",
                    "PATCH",
                    "DELETE",
                ]),
                path: z
                    .string()
                    .optional()
                    .default(""),
                body: z.any().optional(),
            },
        },
        async ({
            worker,
            method,
            path,
            body,
        }) => {
            const suffix =
                path
                    ? `/${path.replace(
                          /^\/+/,
                          ""
                      )}`
                    : "";

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}${suffix}`,
                    body,
                })
            );
        }
    );

    server.registerTool(
        "cloudflare_download_worker",
        {
            description:
                "Download the deployed Worker source.",
            inputSchema: {
                worker: z.string().min(1),
            },
        },
        async ({ worker }) =>
            jsonResult(
                await cloudflareRequest({
                    method: "GET",
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}`,
                })
            )
    );

    server.registerTool(
        "cloudflare_upload_worker",
        {
            description:
                "Upload or replace a Worker using Cloudflare's Worker script endpoint. Body must match the format expected by the Cloudflare API.",
            inputSchema: {
                worker: z.string().min(1),
                body: z.any(),
                contentType: z
                    .string()
                    .optional()
                    .default(
                        "application/javascript"
                    ),
            },
        },
        async ({
            worker,
            body,
            contentType,
        }) =>
            jsonResult(
                await cloudflareRequest({
                    method: "PUT",
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}`,
                    body,
                    contentType,
                })
            )
    );

    server.registerTool(
        "cloudflare_delete_worker",
        {
            description:
                "Delete a Cloudflare Worker.",
            inputSchema: {
                worker: z.string().min(1),
            },
        },
        async ({ worker }) =>
            jsonResult(
                await cloudflareRequest({
                    method: "DELETE",
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}`,
                })
            )
    );

    server.registerTool(
        "cloudflare_worker_versions",
        {
            description:
                "Manage Worker versions.",
            inputSchema: {
                worker: z.string().min(1),
                method: z.enum([
                    "GET",
                    "POST",
                    "DELETE",
                ]),
                versionId: z
                    .string()
                    .optional(),
                body: z.any().optional(),
            },
        },
        async ({
            worker,
            method,
            versionId,
            body,
        }) => {
            const suffix =
                versionId
                    ? `/${encodeURIComponent(
                          versionId
                      )}`
                    : "";

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}/versions${suffix}`,
                    body,
                })
            );
        }
    );

    server.registerTool(
        "cloudflare_worker_deployments",
        {
            description:
                "Create, inspect or delete Worker deployments.",
            inputSchema: {
                worker: z.string().min(1),
                method: z.enum([
                    "GET",
                    "POST",
                    "DELETE",
                ]),
                deploymentId: z
                    .string()
                    .optional(),
                body: z.any().optional(),
            },
        },
        async ({
            worker,
            method,
            deploymentId,
            body,
        }) => {
            const suffix =
                deploymentId
                    ? `/${encodeURIComponent(
                          deploymentId
                      )}`
                    : "";

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}/deployments${suffix}`,
                    body,
                })
            );
        }
    );

    server.registerTool(
        "cloudflare_worker_settings",
        {
            description:
                "Read or modify Worker settings.",
            inputSchema: {
                worker: z.string().min(1),
                method: z.enum([
                    "GET",
                    "PATCH",
                ]),
                body: z.any().optional(),
            },
        },
        async ({
            worker,
            method,
            body,
        }) =>
            jsonResult(
                await cloudflareRequest({
                    method,
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}/settings`,
                    body,
                })
            )
    );

    server.registerTool(
        "cloudflare_worker_secrets",
        {
            description:
                "Manage Worker secrets. This can create, inspect or delete secret bindings. Secret values may be returned by Cloudflare's API where supported.",
            inputSchema: {
                worker: z.string().min(1),
                method: z.enum([
                    "GET",
                    "PUT",
                    "PATCH",
                    "DELETE",
                ]),
                secretName: z
                    .string()
                    .optional(),
                body: z.any().optional(),
            },
        },
        async ({
            worker,
            method,
            secretName,
            body,
        }) => {
            const suffix =
                secretName
                    ? `/${encodeURIComponent(
                          secretName
                      )}`
                    : "";

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}/secrets${suffix}`,
                    body,
                })
            );
        }
    );

    server.registerTool(
        "cloudflare_worker_schedules",
        {
            description:
                "Read or modify Worker Cron Trigger schedules.",
            inputSchema: {
                worker: z.string().min(1),
                method: z.enum([
                    "GET",
                    "PUT",
                ]),
                body: z.any().optional(),
            },
        },
        async ({
            worker,
            method,
            body,
        }) =>
            jsonResult(
                await cloudflareRequest({
                    method,
                    path:
                        `/accounts/${ACCOUNT_ID}/workers/scripts/${encodeURIComponent(
                            worker
                        )}/schedules`,
                    body,
                })
            )
    );

    // ================================================================
    // D1
    // ================================================================

    server.registerTool(
        "cloudflare_d1_request",
        {
            description:
                "Manage and query Cloudflare D1. Supports GET/POST/DELETE endpoints exposed by the D1 API. For SQL queries, use the D1 query endpoint with the appropriate Cloudflare request body.",
            inputSchema: {
                method: z.enum([
                    "GET",
                    "POST",
                    "DELETE",
                ]),
                databaseId: z
                    .string()
                    .optional(),
                path: z
                    .string()
                    .optional()
                    .default(""),
                body: z.any().optional(),
            },
        },
        async ({
            method,
            databaseId,
            path,
            body,
        }) => {
            let apiPath =
                `/accounts/${ACCOUNT_ID}/d1`;

            if (databaseId) {
                apiPath += `/database/${encodeURIComponent(
                    databaseId
                )}`;
            }

            if (path) {
                apiPath += `/${path.replace(
                    /^\/+/,
                    ""
                )}`;
            }

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path: apiPath,
                    body,
                })
            );
        }
    );

    // ================================================================
    // R2
    // ================================================================

    server.registerTool(
        "cloudflare_r2_request",
        {
            description:
                "Manage Cloudflare R2 buckets and objects through the Cloudflare API.",
            inputSchema: {
                method: z.enum([
                    "GET",
                    "POST",
                    "PUT",
                    "PATCH",
                    "DELETE",
                ]),
                bucket: z
                    .string()
                    .optional(),
                path: z
                    .string()
                    .optional()
                    .default(""),
                body: z.any().optional(),
            },
        },
        async ({
            method,
            bucket,
            path,
            body,
        }) => {
            let apiPath =
                `/accounts/${ACCOUNT_ID}/r2`;

            if (bucket) {
                apiPath += `/buckets/${encodeURIComponent(
                    bucket
                )}`;
            }

            if (path) {
                apiPath += `/${path.replace(
                    /^\/+/,
                    ""
                )}`;
            }

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path: apiPath,
                    body,
                })
            );
        }
    );

    // ================================================================
    // KV
    // ================================================================

    server.registerTool(
        "cloudflare_kv_request",
        {
            description:
                "Manage Cloudflare Workers KV namespaces, keys and values.",
            inputSchema: {
                method: z.enum([
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                ]),
                namespaceId: z
                    .string()
                    .optional(),
                path: z
                    .string()
                    .optional()
                    .default(""),
                body: z.any().optional(),
            },
        },
        async ({
            method,
            namespaceId,
            path,
            body,
        }) => {
            let apiPath =
                `/accounts/${ACCOUNT_ID}/storage/kv`;

            if (namespaceId) {
                apiPath += `/namespaces/${encodeURIComponent(
                    namespaceId
                )}`;
            }

            if (path) {
                apiPath += `/${path.replace(
                    /^\/+/,
                    ""
                )}`;
            }

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path: apiPath,
                    body,
                })
            );
        }
    );

    // ================================================================
    // ZONES / DNS
    // ================================================================

    server.registerTool(
        "cloudflare_zone_request",
        {
            description:
                "Manage Cloudflare zones and zone-level resources.",
            inputSchema: {
                method: z.enum([
                    "GET",
                    "POST",
                    "PUT",
                    "PATCH",
                    "DELETE",
                ]),
                zoneId: z
                    .string()
                    .optional(),
                path: z
                    .string()
                    .optional()
                    .default(""),
                body: z.any().optional(),
            },
        },
        async ({
            method,
            zoneId,
            path,
            body,
        }) => {
            let apiPath = "/zones";

            if (zoneId) {
                apiPath += `/${encodeURIComponent(
                    zoneId
                )}`;
            }

            if (path) {
                apiPath += `/${path.replace(
                    /^\/+/,
                    ""
                )}`;
            }

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path: apiPath,
                    body,
                })
            );
        }
    );

    // ================================================================
    // USER API
    // ================================================================

    server.registerTool(
        "cloudflare_user_request",
        {
            description:
                "Manage Cloudflare user-level API resources allowed by the token.",
            inputSchema: {
                method: z.enum([
                    "GET",
                    "POST",
                    "PUT",
                    "PATCH",
                    "DELETE",
                ]),
                path: z
                    .string()
                    .optional()
                    .default(""),
                body: z.any().optional(),
            },
        },
        async ({
            method,
            path,
            body,
        }) => {
            let apiPath = "/user";

            if (path) {
                apiPath += `/${path.replace(
                    /^\/+/,
                    ""
                )}`;
            }

            return jsonResult(
                await cloudflareRequest({
                    method,
                    path: apiPath,
                    body,
                })
            );
        }
    );

    return server;
}

// ================================================================
// HTTP SERVER
// ================================================================

const app = express();

app.use(
    express.json({
        limit: "50mb",
        type: [
            "application/json",
            "application/*+json",
        ],
    })
);

app.get("/health", (_req, res) => {
    res.json({
        status: "ok",
        server:
            "RETR0 Cloudflare Full Access MCP",
        version: "3.0.0",
        cloudflareAccess:
            "READ / WRITE",
        accountId: ACCOUNT_ID,
    });
});

app.post("/mcp", async (req, res) => {
    try {
        /*
         * Stateless Streamable HTTP.
         */
        const server = createServer();

        const transport =
            new StreamableHTTPServerTransport({
                sessionIdGenerator: undefined,
                enableJsonResponse: true,
            });

        await server.connect(transport);

        await transport.handleRequest(
            req,
            res,
            req.body
        );
    } catch (error) {
        console.error(error);

        if (!res.headersSent) {
            res.status(500).json({
                error: "MCP server error",
                message:
                    error instanceof Error
                        ? error.message
                        : String(error),
            });
        }
    }
});

app.listen(
    PORT,
    "127.0.0.1",
    () => {
        console.log(
            `RETR0 Cloudflare MCP v3 running at http://127.0.0.1:${PORT}/mcp`
        );

        console.log(
            `Health check: http://127.0.0.1:${PORT}/health`
        );

        console.log(
            "Cloudflare access: READ / WRITE"
        );

        console.log(
            "WARNING: destructive Cloudflare operations are available."
        );

        console.log(
            "Never expose this MCP endpoint outside localhost."
        );
    }
);