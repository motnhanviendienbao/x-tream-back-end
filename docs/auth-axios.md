# Axios Auth Guide (Login/Register + Filter)

## 1) API endpoints

### Register

- **POST** `/auth/register`

Request body:

```json
{ "username": "user1", "password": "123456" }
```

Success response:

```json
{ "message": "User registered successfully" }
```

### Login

- **POST** `/auth/login`

Request body:

```json
{ "username": "user1", "password": "123456" }
```

Success response:

```json
{ "token": "TOKEN_ID:RAW_TOKEN" }
```

## 2) Token format + cách gửi lên backend

Backend đang dùng **HTTP Basic**. Token trả về có dạng:

- `TOKEN_ID:RAW_TOKEN`

Khi gọi các API protected, FE cần set header:

- `Authorization: Basic base64("TOKEN_ID:RAW_TOKEN")`

## 3) Lưu token

Ví dụ lưu vào `localStorage`:

```js
localStorage.setItem("authToken", token); // token = "123:abcd..."
```

## 4) Axios instance + Request interceptor (auto attach Authorization)

```js
import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("authToken"); // "id:raw"
  if (token) {
    config.headers.Authorization = `Basic ${btoa(token)}`;
  }
  return config;
});
```

## 5) Filter login/register (không attach token cho `/auth/**`)

```js
api.interceptors.request.use((config) => {
  const url = config.url || "";
  const isAuthApi = url.startsWith("/auth/");
  if (isAuthApi) return config;

  const token = localStorage.getItem("authToken"); // "id:raw"
  if (token) {
    config.headers.Authorization = `Basic ${btoa(token)}`;
  }
  return config;
});
```

## 6) Response interceptor: auto logout khi 401/403

Backend trả lỗi theo format:

```json
{
  "status": 401,
  "message": "Invalid username or password",
  "path": "/auth/login",
  "timestamp": "2026-03-17T13:29:00+07:00",
  "details": null
}
```

FE có thể logout khi token hết hạn / bị lock / session expired:

```js
api.interceptors.response.use(
  (res) => res,
  (err) => {
    const status = err?.response?.status;

    if (status === 401 || status === 403) {
      localStorage.removeItem("authToken");
      // ví dụ:
      // window.location.href = "/login";
    }

    return Promise.reject(err);
  }
);
```

## 7) Ví dụ call

```js
// login
const { data } = await api.post("/auth/login", { username, password });
localStorage.setItem("authToken", data.token);

// gọi API protected
const res = await api.get("/some-protected-api");
console.log(res.data);
```

