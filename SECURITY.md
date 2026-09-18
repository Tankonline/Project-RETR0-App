# Security Policy

## Supported Versions

Only the latest production release is supported for security updates.

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Reporting a Vulnerability

We value the security of our users. If you find a vulnerability, please report it privately.

### How to report
Please use **GitHub Private Vulnerability Reporting**. Navigate to the "Security" tab of this repository on GitHub and select "Advisories" -> "Report a vulnerability".

**Do not** disclose vulnerabilities in public issues or community forums until they are resolved.

### What information to include
- Description of the vulnerability.
- Steps to reproduce (Proof of Concept).
- Potential impact.

## Scope

- **Application Logic:** Vulnerabilities in the installer and community features.
- **Data Handling:** Secure handling of installation IDs and game data.

**Out of Scope:**
- Vulnerabilities in third-party services (YouTube, Cloudflare, etc.).
- Bypasses of Android system security or Shizuku service itself.
- User-end data loss resulting from unauthorized modifications.

## Security Credentials
Release signing material and service tokens are managed securely through environment variables and GitHub Secrets. They are never committed to the repository.
