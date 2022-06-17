CREATE TABLE OAUTH2_REGISTERED_CLIENT 
(
    ID VARCHAR2(100) NOT NULL,
    CLIENT_ID VARCHAR2(100) NOT NULL,
    CLIENT_ID_ISSUED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- 经测试验证亦可允许NULL
    CLIENT_SECRET VARCHAR2(200) DEFAULT NULL,
    CLIENT_SECRET_EXPIRES_AT TIMESTAMP DEFAULT NULL,
    CLIENT_NAME VARCHAR2(200) NOT NULL,
    CLIENT_AUTHENTICATION_METHODS VARCHAR2(1000) NOT NULL,
    AUTHORIZATION_GRANT_TYPES VARCHAR2(1000) NOT NULL,
    REDIRECT_URIS VARCHAR2(1000) DEFAULT NULL,
    SCOPES VARCHAR2(1000) NOT NULL,
    CLIENT_SETTINGS VARCHAR2(2000) NOT NULL,
    TOKEN_SETTINGS VARCHAR2(2000) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE OAUTH2_AUTHORIZATION 
(
    ID VARCHAR(100) NOT NULL,
    REGISTERED_CLIENT_ID VARCHAR(100) NOT NULL,
    PRINCIPAL_NAME VARCHAR(200) NOT NULL,
    AUTHORIZATION_GRANT_TYPE VARCHAR(100) NOT NULL,
    ATTRIBUTES VARCHAR2(3000) DEFAULT NULL,
    STATE VARCHAR(500) DEFAULT NULL,
    AUTHORIZATION_CODE_VALUE VARCHAR2(3000) DEFAULT NULL,
    AUTHORIZATION_CODE_ISSUED_AT TIMESTAMP DEFAULT NULL,
    AUTHORIZATION_CODE_EXPIRES_AT TIMESTAMP DEFAULT NULL,
    AUTHORIZATION_CODE_METADATA VARCHAR2(3000) DEFAULT NULL,
    ACCESS_TOKEN_VALUE VARCHAR2(3000) DEFAULT NULL,
    ACCESS_TOKEN_ISSUED_AT TIMESTAMP DEFAULT NULL,
    ACCESS_TOKEN_EXPIRES_AT TIMESTAMP DEFAULT NULL,
    ACCESS_TOKEN_METADATA VARCHAR2(3000) DEFAULT NULL,
    ACCESS_TOKEN_TYPE VARCHAR(100) DEFAULT NULL,
    ACCESS_TOKEN_SCOPES VARCHAR(1000) DEFAULT NULL,
    OIDC_ID_TOKEN_VALUE VARCHAR2(3000) DEFAULT NULL,
    OIDC_ID_TOKEN_ISSUED_AT TIMESTAMP DEFAULT NULL,
    OIDC_ID_TOKEN_EXPIRES_AT TIMESTAMP DEFAULT NULL,
    OIDC_ID_TOKEN_METADATA VARCHAR2(3000) DEFAULT NULL,
    REFRESH_TOKEN_VALUE VARCHAR2(3000) DEFAULT NULL,
    REFRESH_TOKEN_ISSUED_AT TIMESTAMP DEFAULT NULL,
    REFRESH_TOKEN_EXPIRES_AT TIMESTAMP DEFAULT NULL,
    REFRESH_TOKEN_METADATA VARCHAR2(3000) DEFAULT NULL,
    PRIMARY KEY (ID)
);

-- 脚本模板
insert into OAUTH2_REGISTERED_CLIENT (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings)
values ('a6b156d1-b2c9-40d4-885b-b1db2c670f64', 'rico-client', to_timestamp('17-06-2022 15:38:08.062000', 'dd-mm-yyyy hh24:mi:ss.ff'), '{noop}123', null, 'a6b156d1-b2c9-40d4-885b-b1db2c670f64', 'client_secret_basic', 'refresh_token,authorization_code', 'http://127.0.0.1:7090/spring-oauth2-code-v1,http://127.0.0.1:8080/authorized,http://127.0.0.1:7090/oauth2/callback,https://oidcdebugger.com/debug,http://127.0.0.1:8080/login/oauth2/code/rico-client-oidc', 'openid,test.read', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');
commit;

