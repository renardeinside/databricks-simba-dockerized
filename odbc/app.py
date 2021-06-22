import os
import pyodbc
from tabulate import tabulate

for var in ["DATABRICKS_HOST", "DATABRICKS_TOKEN", "DATABRICKS_HTTP_PATH"]:
    if var not in os.environ:
        raise Exception(f"Environment variable {var} is not defined")

DATABRICKS_HOST = os.environ["DATABRICKS_HOST"]
DATABRICKS_TOKEN = os.environ["DATABRICKS_TOKEN"]
DATABRICKS_HTTP_PATH = os.environ["DATABRICKS_HTTP_PATH"]
DRIVER_PATH=os.environ.get("SIMBA_DRIVER_PATH", "/opt/simba/spark/lib/64/libsparkodbc_sb64.so") # default location on Debian

connection_string = "".join(
    [
        f"DRIVER={DRIVER_PATH}",
        f";Host={DATABRICKS_HOST}",
        ";PORT=443",
        f";HTTPPath={DATABRICKS_HTTP_PATH}",
        ";AuthMech=11",
        ";SSL=1",
        ";ThriftTransport=2",
        ";SparkServerType=3",
        ";Auth_Flow=0",
        f";Auth_AccessToken={DATABRICKS_TOKEN}"
    ]
)

if __name__ == "__main__":
    cnxn = pyodbc.connect(connection_string, autocommit=True)
    cursor = cnxn.cursor()
    cursor.execute("SHOW TABLES")
    result = cursor.fetchall()
    print(tabulate(result))
