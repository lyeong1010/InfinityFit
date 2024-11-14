# InfinityFit Security Policy

To ensure the safe use of the InfinityFit project, please follow these security guidelines.

## Remote Artifacts

InfinityFit allows users to download and use data and models provided by external sources. However, files downloaded from outside sources may pose security risks. We recommend using files in the **`.safetensors` format** whenever possible, as this format is designed to prevent arbitrary code execution on your system.

### Safe File Loading

- Whenever possible, download and load files in the `.safetensors` format.
- Formats such as `.pickle` are not recommended; if necessary, please review them thoroughly before use.

## Remote Code

InfinityFit supports various model architectures and external resources. When using the `trust_remote_code=True` parameter, please review the file contents to ensure their safety. **Set a specific version** if needed to protect against unexpected repository updates.

### Using External Tools

InfinityFit’s Agent framework allows for the downloading of remote tools that can be executed locally. When using these tools, inspect the code to ensure the security of your runtime and local environment.

## Reporting a Vulnerability

If you discover a security vulnerability in the project, please email `sohee2121@gmail.com`. Our team will review the issue and provide guidance on the next steps. You may also report open-source vulnerabilities through the Huntr platform.

---

By following this security policy, you can help minimize potential security risks when using InfinityFit. Please feel free to reach out with any additional security questions or concerns!
