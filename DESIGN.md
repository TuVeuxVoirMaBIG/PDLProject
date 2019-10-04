Design
======

The vision and goals of libnetwork are highlighted in [roadmap](../ROADMAP.md).
This document describes how libnetwork has been designed in order to achieve this.
Requirements for individual releases can be found on the [Project Page](https://github.com/docker/libnetwork/wiki).

Many of the design decisions are inspired by the learnings from the Docker networking design as of Docker v1.6.
Please refer to this [Docker v1.6 Design](legacy.md) document for more information on networking design as of Docker v1.6.

## Goal

libnetwork project will follow Docker and Linux philosophy of developing small, highly modular and composable tools that work well independently.
Libnetwork aims to satisfy that composable need for Networking in Containers.

## The Container Network Model

Libnetwork implements Container Network Model (CNM) which formalizes the steps required to provide networking for containers while providing an abstraction that can be used to support multiple network drivers. The CNM is built on 3 main components (shown below)

![](/media/Class_Diagram.png)
