import requests
import json
import pandas as pd
from dotenv import dotenv_values


def llamando_API():
    config = {
        **dotenv_values(".env")
    }

    url = config["token"].join(config["url"].split("[Aquí va tu Token]"))

    response= requests.get(url)

    if response.status_code==200:
        data= json.loads(response.content)
        observations = data['Series'][0]['OBSERVATIONS']
        df = pd.DataFrame(observations)
        
        selected_columns = ['TIME_PERIOD', 'OBS_VALUE', 'COBER_GEO']
        df = df[selected_columns]
        
        title = data['Header']['Name']
        df.to_csv(f'/INEGI/data/Población total ocupada, subocupada y desocupada.csv', index=False)