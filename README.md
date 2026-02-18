# Agency Outreach SaaS — README
A multi‑tenant SaaS platform designed for small B2B agencies that need simple, affordable, personalised email outreach for multiple clients. The system provides template management, contact handling, campaign automation, and low‑cost email delivery through providers like Amazon SES.
## 🎯 Purpose
Small B2B agencies increasingly rely on personalised outbound outreach to generate leads, promote client services, and run campaigns. Existing tools are either:
too complex (HubSpot, Outreach, SalesLoft)
too generic (Mailchimp, Brevo)
too expensive (Apollo, Lemlist)
not built for multi‑client workflows
This platform fills the gap with a simple, affordable, agency‑focused outreach tool.
## 🚀 Core Features
Multi‑tenant architecture
Each agency has its own organisation (tenant)
Users belong to organisations with role‑based access
All data (templates, contacts, campaigns) is isolated per tenant
Template management
Create, edit, and version email templates
Use placeholders like {{firstName}}, {{companyName}}
Test-send templates to verify formatting
Contact management
Add contacts individually or via CSV import
Support for tags and custom fields (JSON)
Per‑client segmentation
Campaign automation
Create campaigns using templates + contact lists
Schedule or trigger immediately
Background worker handles bulk sending
Track delivery, opens, clicks, and bounces
Low-cost email delivery
Integrates with Amazon SES (primary)
Pluggable provider interface for Mailgun/SendGrid
Supports per-client sending domains
Agency‑friendly workflows
Separate workspaces for each client
Per-client templates and analytics
Easy switching between client accounts
Billing & plans
Stripe Billing integration
Usage-based limits (contacts, emails/month)
Automatic plan enforcement
## 🧩 System Architecture
High-level components
API Service (Spring Boot)  
Handles auth, tenancy, templates, contacts, campaigns, and provider integration.
Worker Service  
Processes queued email jobs, retries, and webhook events.
Frontend (React/Next.js)  
Provides dashboards, editors, and campaign management UI.
Database (PostgreSQL)  
Stores all tenant-scoped data with tenant_id on every row.
Queue (AWS SQS or RabbitMQ)  
Ensures reliable background sending.
Email Provider (SES)  
Handles actual email delivery.
## 🏗️ Domain Model Overview
Tenancy
Tenant — organisation (agency)
User — belongs to a tenant, has a role
Outreach data
Template — subject, body, placeholders, version
Contact — email, name, tags, custom fields
Campaign — template + audience + schedule
CampaignRecipient — per-contact send status
Billing
Plan — limits for contacts, emails, clients
Subscription — Stripe-managed lifecycle
## 🔌 API Endpoints (MVP)
### Auth
- POST /auth/register
- POST /auth/login
### Templates
- GET /templates
- POST /templates
- PUT /templates/{id}
- POST /templates/{id}/test-send
### Contacts
- POST /contacts
- POST /contacts/bulk
- GET /contacts?tag=...
### Campaigns
- POST /campaigns
- GET /campaigns
- GET /campaigns/{id}
- POST /campaigns/{id}/start
## 🛠️ Technology Stack
### Backend
- Java 21
- Spring Boot (Web, Security, Data JPA)
- PostgreSQL
- AWS SQS or RabbitMQ
- Amazon SES
- Handlebars/FreeMarker for template rendering
### Frontend
- React or Next.js
- TypeScript
- Component library (MUI/Chakra)
- Infrastructure
- AWS ECS/Fargate or EC2
- Terraform or AWS CDK
- CloudWatch for logs and metrics
### 🔄 Background Job Flow
- User creates a campaign
- System generates a job per contact
### Worker service:
- Fetches template + contact
- Renders personalised email
- Sends via SES
- Updates CampaignRecipient
- Dashboard updates with stats
## 📈 Market Positioning
This SaaS targets small B2B agencies that need:
personalised outreach
multi-client support
low cost
simple workflows
The broader email marketing market is crowded, but this niche remains underserved because incumbents optimise for enterprise sales teams or generic marketing use cases.
Why this niche is attractive
Agencies are multiplying faster than SaaS platforms
Outbound outreach is growing as inbound channels saturate
Agencies need multi-client features that most tools lack
AI-driven personalisation is now expected
Business potential
200–500 paying agencies globally
£29–£99/month pricing
£100k–£500k ARR with a lean team
Expandable into PR, recruiting, events, and other verticals
## 🧭 Roadmap
- Phase 1 — MVP
    - Multi-tenant auth
    - Templates + test send
    - Contacts + CSV import
    - Campaign creation + sending
    - Basic dashboard
    - SES integration
- Phase 2 — Agency workflows
Client workspaces
Per-client sending domains
Template libraries per client
Client-level analytics
- Phase 3 — AI enhancements
AI-assisted template generation
AI personalisation suggestions
AI-based contact enrichment
- Phase 4 — Growth
Public website + onboarding
Stripe Billing
Referral program
Marketplace for templates
## 📜 License
Choose a license appropriate for your goals (MIT, Apache 2.0, or commercial).