build-odbc:
	docker build -t databricks-simbda-odbc ./odbc

run-odbc:
	docker run -it --env-file=.env databricks-simbda-odbc