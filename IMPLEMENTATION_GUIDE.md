# OutBack Implementation Guide

## ✅ Completed Features (MVP Phase 1)

### 1. **Multi-Tenant Architecture**
- ✅ Tenant model with audit fields (created_at, updated_at)
- ✅ User model with tenant association
- ✅ Tenant-scoped data isolation across all entities
- ✅ JWT-based authentication with tenant context

### 2. **Authentication & Security**
- ✅ User registration and login endpoints
- ✅ JWT token generation with tenant/user context
- ✅ Password hashing with BCrypt
- ✅ JwtAuthenticationFilter for token validation
- ✅ SecurityConfig with CSRF disabled and stateless session management
- ✅ Custom exception handling (UnauthorizedException, ForbiddenException, ResourceNotFoundException)

### 3. **Template Management**
- ✅ Template CRUD operations
- ✅ Version tracking for templates
- ✅ Tenant-scoped template queries
- ✅ Pagination support
- ✅ Template validation (name, subject, body required)

### 4. **Contact Management**
- ✅ Contact CRUD operations
- ✅ Tag support for contact segmentation
- ✅ Custom fields support (JSON format)
- ✅ Bulk contact creation
- ✅ Tag-based filtering
- ✅ Pagination support
- ✅ Email validation

### 5. **Campaign Management**
- ✅ Campaign CRUD operations
- ✅ Campaign recipient tracking (CampaignRecipient entity)
- ✅ Campaign status management (DRAFT, SCHEDULED, RUNNING, COMPLETED, FAILED)
- ✅ Recipient status tracking (PENDING, SENT, OPENED, CLICKED, BOUNCED, FAILED)
- ✅ Campaign statistics (total recipients, sent count, failed count)
- ✅ Campaign start workflow
- ✅ Pagination support

### 6. **Template Rendering**
- ✅ Mustache template engine integration
- ✅ Placeholder rendering ({{firstName}}, {{companyName}}, etc.)
- ✅ Template rendering service for email personalization

### 7. **Email Service**
- ✅ AWS SES integration
- ✅ HTML and plain text email support
- ✅ Template rendering with variable substitution
- ✅ Configurable sender email
- ✅ Error handling with custom exceptions

### 8. **Database**
- ✅ PostgreSQL configuration
- ✅ JPA/Hibernate integration
- ✅ Audit fields on all entities (createdAt, updatedAt)
- ✅ Proper foreign key relationships
- ✅ Indexed queries for tenant isolation

### 9. **API Documentation**
- ✅ Springdoc OpenAPI/Swagger integration
- ✅ Available at http://localhost:8080/api/swagger-ui.html

### 10. **Error Handling**
- ✅ Global exception handler with @ControllerAdvice
- ✅ Custom exception classes
- ✅ Meaningful error responses
- ✅ Validation error handling with field-level details

### 11. **Testing**
- ✅ UserServiceTest - User registration, validation, password change
- ✅ PasswordEncoderServiceTest - Password hashing and validation
- ✅ TemplateRenderingServiceTest - Template rendering with variables

### 12. **Configuration**
- ✅ Environment-based configuration (YAML)
- ✅ JWT secret and expiration configuration
- ✅ AWS region and SES configuration
- ✅ Database connection pooling (HikariCP)
- ✅ Logging configuration

---

## 🚀 API Endpoints

### Authentication
```
POST /api/auth/register
{
  "email": "user@example.com",
  "password": "securePassword123",
  "tenantName": "My Agency"
}

POST /api/auth/login
{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

### Templates
```
POST /api/templates (Create template)
GET /api/templates (List templates, paginated)
GET /api/templates/{id} (Get template by ID)
PUT /api/templates/{id} (Update template)
DELETE /api/templates/{id} (Delete template)
```

### Contacts
```
POST /api/contacts (Create contact)
GET /api/contacts (List contacts, paginated)
GET /api/contacts?tag=vip (Filter by tag)
GET /api/contacts/{id} (Get contact by ID)
PUT /api/contacts/{id} (Update contact)
DELETE /api/contacts/{id} (Delete contact)
POST /api/contacts/bulk (Bulk create contacts)
```

### Campaigns
```
POST /api/campaigns (Create campaign)
GET /api/campaigns (List campaigns, paginated)
GET /api/campaigns/{id} (Get campaign by ID)
PUT /api/campaigns/{id} (Update campaign)
DELETE /api/campaigns/{id} (Delete campaign)
POST /api/campaigns/{id}/start (Start campaign sending)
GET /api/campaigns/{id}/recipients (Get campaign recipients)
```

### User
```
GET /api/users/me (Get current user)
PUT /api/users/{id} (Update user)
DELETE /api/users/{id} (Delete user)
POST /api/users/{id}/change-password (Change password)
```

---

## 📦 Dependencies Added

```gradle
// JWT
implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'

// Swagger/OpenAPI
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'

// Template Rendering
implementation 'com.github.spullara.mustache.java:compiler:0.9.10'

// Testing
testImplementation 'org.springframework.security:spring-security-test'
```

---

## 🔧 Setup Instructions

### 1. Database Setup
```bash
# Create PostgreSQL database
createdb agency_outreach

# Update application.yaml with your database credentials
```

### 2. Environment Variables
```bash
export JWT_SECRET="your-256-bit-secret-key-here-minimum-32-chars"
export AWS_REGION="us-east-1"
export AWS_SES_FROM_EMAIL="noreply@yourdomain.com"
```

### 3. AWS SES Setup
- Configure AWS credentials using `~/.aws/credentials` or environment variables
- Verify sender email in AWS SES console
- Request production access (exit sandbox)

### 4. Build & Run
```bash
./gradlew build
./gradlew bootRun
```

### 5. Access Swagger UI
```
http://localhost:8080/api/swagger-ui.html
```

---

## 🔐 Security Features

1. **JWT Authentication**: All endpoints (except /auth/*) require valid JWT token
2. **Multi-Tenant Isolation**: Every query filters by tenant_id from JWT
3. **Password Hashing**: BCrypt with configurable strength
4. **CSRF Protection**: Disabled for API (stateless)
5. **Input Validation**: @Valid annotations on all request bodies
6. **Exception Handling**: No stack traces exposed to clients

---

## 🗄️ Database Schema

### Key Entities
- **Tenant**: Organization/Agency
- **User**: User account with role (ADMIN, USER)
- **Template**: Email template with version tracking
- **Contact**: Email contact with tags and custom fields
- **Campaign**: Email campaign with recipient tracking
- **CampaignRecipient**: Per-contact campaign status and delivery tracking

### All entities include:
- `id` (Primary Key)
- `tenant_id` (Foreign Key for multi-tenancy)
- `created_at` (Timestamp)
- `updated_at` (Timestamp)

---

## 📊 Testing

Run unit tests:
```bash
./gradlew test
```

Test files:
- `UserServiceTest.java` - User operations and password validation
- `PasswordEncoderServiceTest.java` - Password hashing
- `TemplateRenderingServiceTest.java` - Template rendering with Mustache

---

## 🚧 Outstanding (Phase 2-4 Features)

### Phase 2: Agency Workflows
- [ ] Client workspaces (multi-level tenancy)
- [ ] Per-client sending domains
- [ ] Client-level analytics dashboard

### Phase 3: AI Enhancements
- [ ] AI-assisted template generation
- [ ] AI personalization suggestions
- [ ] Contact enrichment via APIs

### Phase 4: Advanced Features
- [ ] Background job queue (AWS SQS/RabbitMQ)
- [ ] Email delivery webhooks
- [ ] Stripe Billing integration
- [ ] Usage-based plan enforcement
- [ ] Campaign scheduling with Quartz

### Quick Wins for Enhancement
- [ ] Add @Transactional to service methods
- [ ] Implement database indexing strategy
- [ ] Add Redis caching for templates
- [ ] Structured JSON logging
- [ ] Monitoring/metrics with Micrometer
- [ ] GraphQL API layer
- [ ] OpenAPI v3.1 compliance

---

## 💡 Example Workflows

### 1. Register New Agency
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@agency.com",
    "password": "SecurePass123!",
    "tenantName": "My Digital Agency"
  }'
```

### 2. Create Template
```bash
curl -X POST http://localhost:8080/api/templates \
  -H "Authorization: Bearer {JWT_TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Welcome Email",
    "subject": "Welcome to {{companyName}}",
    "body": "Hi {{firstName}},\n\nWelcome aboard!"
  }'
```

### 3. Create Campaign
```bash
curl -X POST http://localhost:8080/api/campaigns \
  -H "Authorization: Bearer {JWT_TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Q1 Outreach",
    "templateId": 1,
    "contactIds": [1, 2, 3],
    "scheduledTime": "2026-03-01T09:00:00"
  }'
```

---

## 🔄 Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                   REST API Controllers                      │
│  (Auth, Template, Contact, Campaign, User)                 │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│              JWT Authentication Filter                      │
│  (Validates token, extracts tenant/user context)          │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                   Service Layer                            │
│  (Auth, User, Template, Contact, Campaign Services)       │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│              Repository Layer (JPA)                        │
│  (Database queries with tenant filtering)                 │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│           PostgreSQL Database                              │
│  (Multi-tenant data store)                                │
└─────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│         Email Service (AWS SES)                             │
│  ├─ Template Rendering (Mustache)                          │
│  ├─ Email Sending                                          │
│  └─ Error Handling                                         │
└──────────────────────────────────────────────────────────────┘
```

---

## 📝 Notes

- All timestamps are in UTC
- Pagination defaults: page=0, size=20
- JWT expiration: 24 hours
- Password minimum: 8 characters recommended
- Template rendering uses Mustache syntax: `{{variable}}`


