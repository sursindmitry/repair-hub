# Repair Hub
[![codecov](https://codecov.io/gh/sursindmitry/repair-hub/graph/badge.svg?token=3ELIXYK887)](https://codecov.io/gh/sursindmitry/repair-hub)

### Usage

To start an application you need to pass variables to `.env` file.
#### Example:

```agsl
# PostgreSQL
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=repair_db
POSTGRES_USERNAME=root
POSTGRES_PASSWORD=root

# Email
MAIL_USERNAME= YOUR EMAIL
MAIL_PASSWORD= YOUR GOOGLE APPLICATION PASSWORD
HOUR_EXPIRATION_TOKEN_EMAIL=24

# JWT
JWT_SECRET=YOUR JWT SECRET
HOUR_EXPIRATION_TOKEN_JWT=24
```