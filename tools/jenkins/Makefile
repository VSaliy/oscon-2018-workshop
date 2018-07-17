TAG?=latest
DHUSER?=rprakashg


build:
	docker build -t $(DHUSER)/jenkins .

push:
	docker push $(DHUSER)/jenkins

run:
	docker run -p 8080:8080 -p 50000:50000 $(DHUSER)/jenkins
