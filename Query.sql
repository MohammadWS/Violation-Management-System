INSERT INTO users (username, password, email, enabled, account_non_locked, created_at, updated_at)
VALUES
    ('admin', 'n123', 'admin@example.com', 1, 1, SYSDATETIME(), SYSDATETIME()),
    ('jane_smith', 'n111', 'jane@example.com', 1, 1, SYSDATETIME(), SYSDATETIME())