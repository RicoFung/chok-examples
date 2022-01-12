
CREATE TABLE oauth2_registered_client
(
    id                            varchar2(100)                        NOT NULL,
    client_id                     varchar2(100)                        NOT NULL,
    client_id_issued_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar2(200)                        NULL,
    client_secret_expires_at      timestamp                           NULL,
    client_name                   varchar2(200)                        NOT NULL,
    client_authentication_methods varchar2(1000)                       NOT NULL,
    authorization_grant_types     varchar2(1000)                       NOT NULL,
    redirect_uris                 varchar2(1000)                       NULL,
    scopes                        varchar2(1000)                       NOT NULL,
    client_settings               varchar2(2000)                       NOT NULL,
    token_settings                varchar2(2000)                       NOT NULL
);
alter table oauth2_registered_client
  add primary key (id);

CREATE TABLE oauth2_authorization_consent
(
    registered_client_id varchar2(100)  NOT NULL,
    principal_name       varchar2(200)  NOT NULL,
    authorities          varchar2(1000) NOT NULL
);
alter table oauth2_authorization_consent
  add primary key (registered_client_id, principal_name);

CREATE TABLE oauth2_authorization
(
    id                            varchar2(100)  NOT NULL,
    registered_client_id          varchar2(100)  NOT NULL,
    principal_name                varchar2(200)  NOT NULL,
    authorization_grant_type      varchar2(100)  NOT NULL,
    attributes                    varchar2(4000) NULL,
    state                         varchar2(500)  NULL,
    authorization_code_value      blob          NULL,
    authorization_code_issued_at  timestamp     NULL,
    authorization_code_expires_at timestamp     NULL,
    authorization_code_metadata   varchar2(2000) NULL,
    access_token_value            blob          NULL,
    access_token_issued_at        timestamp     NULL,
    access_token_expires_at       timestamp     NULL,
    access_token_metadata         varchar2(2000) NULL,
    access_token_type             varchar2(100)  NULL,
    access_token_scopes           varchar2(1000) NULL,
    oidc_id_token_value           blob          NULL,
    oidc_id_token_issued_at       timestamp     NULL,
    oidc_id_token_expires_at      timestamp     NULL,
    oidc_id_token_metadata        varchar2(2000) NULL,
    refresh_token_value           blob          NULL,
    refresh_token_issued_at       timestamp     NULL,
    refresh_token_expires_at      timestamp     NULL,
    refresh_token_metadata        varchar2(2000) NULL
);
alter table oauth2_authorization
  add primary key (id);