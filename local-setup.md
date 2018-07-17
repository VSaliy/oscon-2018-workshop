# Local development setup

## Installing Docker
Docker needs to be installed on your local development machine to complete this tutorial. If your development machine is OSX please refer to this [article](https://docs.docker.com/docker-for-mac/install/) for instructions on installing Docker. If your development machine is Windows 10 or above, please refer to this [article](https://docs.docker.com/docker-for-windows/install/)

If your development machine is running older legacy OS, you can install docker toolbox, see more info [here](https://docs.docker.com/toolbox/toolbox_install_windows/) (Be warned :))

## Installing Spring Tool Suite (STS)
Click [here](https://spring.io/tools/sts/all) to download Spring Tool Suite

If you are new to Spring Tool Suite please refer to [this](https://spring.io/guides/gs/sts/) getting started guide.

## Installing Java
Java development kit version 8 or above must be installed. Download and Install Java from Oracle [site](http://www.oracle.com/technetwork/java/javase/downloads/index.html)


## Installing GIT
Refer to this [link](https://gist.github.com/derhuerst/1b15ff4652a867391f03) for installing git on your development machine.

## Installing Maven
OSX users can install maven using Homebrew by running command below. Homebrew must be installed.

```sh
brew install maven
```

Windows users can install maven using chocolatey by running command below. Chocolotey must be installed.

```sh
choco install maven
```

## Installing Gradle
Follow instructions at [gradle.org](https://gradle.org/) to install gradle

## Installing SpringBoot CLI
SpringBoot CLI is an optional component that allows you to quickly prototype SpringBoot applications. OSX users can simply install it by running command below.

```sh
brew tap pivotal/tap
brew install springboot
```

If you are on windows and use Scoop, you can install SpringBoot CLI using commands below.

```sh
scoop bucket add extras
scoop install springboot
```

## Installing Swagger Codegen
Swagger codegen must be installed to generate server and client stub for APIs

### OSX Users
OSX users can install swagger-codegen using Homebrew by simply running command below

```

brew install swagger-codegen

```
### Windows users
Windows users can leverage docker for running swagger-codegen. For more info [see](https://github.com/swagger-api/swagger-codegen#development-in-docker). Swagger codegen can also be triggered from Swaggerhub.

