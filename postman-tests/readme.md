## Prerequisites

It's not required, but you can install a tool called Newman to run the tests using Command Line.
Otherwise, you can import the Collection and Environment Variables in Postman and run them in it.

### Installing Newman

Newman is a package of Node.js, and requires both node.js and npm to be installed on your system in order download and install it. The following links provide details on how to install Newman on your computer.

**Windows**: https://blog.postman.com/installing-newman-on-windows/

**Linux**: https://linuxpip.org/install-newman-ubuntu/

**Mac-OS**: https://support.postman.com/hc/en-us/articles/115003703325-How-to-install-Newman

## Instructions

1. Download the tests Collection and the Environment Variables (*Jetbrains Hibernate Tests.postman_collection.json*, *Jetbrains Hibernate Test Env.postman_environment.json*).
2. Navigate to the directory containing these files.
3. Open Command Line in this directory.
4. Run the tests using Newman. Example: `newman run "Jetbrains Hibernate Tests.postman_collection.json" --environment "Jetbrains Hibernate Test Env.postman_environment.json"`