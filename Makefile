build-odbc:
	docker build -t databricks-simba-odbc ./odbc

run-odbc:
	docker run -it --env-file=.env databricks-simba-odbc

build-jdbc:
	docker build -t databricks-simba-jdbc ./jdbc

run-jdbc:
	docker run -it --env-file=.env databricks-simba-jdbc