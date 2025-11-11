import requests
import yaml
import os

CONFIG_SERVER_URL = "http://localhost:8888"  # ton serveur config
APPLICATION_NAME = "invoice_service"
PROFILE = "default"  # dev, prod, etc.

def load_config():
    url = f"{CONFIG_SERVER_URL}/{APPLICATION_NAME}/{PROFILE}"
    try:
        response = requests.get(url)
        response.raise_for_status()
        # Spring Cloud Config Server retourne JSON avec 'propertySources'
        property_sources = response.json().get("propertySources", [])
        config = {}
        for source in property_sources:
            config.update(source.get("source", {}))
        return config
    except Exception as e:
        print("Erreur lors du chargement de la configuration :", e)
        return {}
