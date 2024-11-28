Configuration Guide
===================

This document provides an overview of configuration options, default settings, and customization methods for the **InfinityFit** project. Use this guide to effectively set up and utilize the project.

---

Configuration Options
---------------------

### **1. Environment Variables**

**InfinityFit** uses the following environment variables to control the project’s behavior. These can be configured in a `.env` file.

+------------------+----------------------------------------------+-------------------+
| Variable Name    | Description                                  | Default Value     |
+==================+==============================================+===================+
| `FLASK_ENV`      | Flask mode (`development` or `production`)   | `development`     |
+------------------+----------------------------------------------+-------------------+
| `DATABASE_URL`   | Database connection URL                     | `sqlite:///app.db`|
+------------------+----------------------------------------------+-------------------+
| `SECRET_KEY`     | Application secret key                      | Auto-generated    |
+------------------+----------------------------------------------+-------------------+
| `API_RATE_LIMIT` | API rate limit (requests per second)         | `60`              |
+------------------+----------------------------------------------+-------------------+

**Example `.env` File**:
.. code-block:: env

    FLASK_ENV=production
    DATABASE_URL=sqlite:///app.db
    SECRET_KEY=your_secret_key
    API_RATE_LIMIT=100
