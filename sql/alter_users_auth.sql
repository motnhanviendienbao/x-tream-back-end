-- Add columns required for login throttling + lock duration.
-- SQL Server (x_tream.dbo.users)

ALTER TABLE [dbo].[users]
ADD [lock_until] datetimeoffset NULL;

ALTER TABLE [dbo].[users]
ADD [failed_window_start] datetimeoffset NULL;

