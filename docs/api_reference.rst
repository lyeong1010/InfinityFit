API Reference
=============

This document provides an overview of the available APIs for the InfinityFit web application.

---

User Registration API
----------------------

### Endpoint:

### Description:
This API is used for user registration.

**Request Example:**
.. code-block:: json

    {
        "username": "lyeong1010",
        "email": "sohee2125@gmail.com",
        "password": "password123"
    }

{
    "status": "success",
    "message": "User registered successfully.",
    "data": {
        "user_id": 12345,
        "username": "lyeong1010",
        "email": "sohee2125@gmail.com"
    }
}

POST /api/login
{
    "username": "lyeong1010",
    "password": "password123"
}
{
    "status": "success",
    "message": "Login successful.",
    "data": {
        "token": "abcd1234tokenvalue",
        "user_id": 12345,
        "username": "lyeong1010"
    }
}

GET /api/user/profile
{
    "Authorization": "Bearer abcd1234tokenvalue"
}
{
    "status": "success",
    "message": "User profile retrieved successfully.",
    "data": {
        "user_id": 12345,
        "username": "lyeong1010",
        "email": "sohee2125@gmail.com",
        "fitness_level": "Intermediate",
        "goal": "Weight Loss"
    }
}
POST /api/logout
{
    "Authorization": "Bearer abcd1234tokenvalue"
}
{
    "status": "success",
    "message": "Logged out successfully."
}
