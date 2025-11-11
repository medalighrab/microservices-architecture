from django.apps import AppConfig
from py_eureka_client import eureka_client
import requests
import yaml
from django.conf import settings

CONFIG_SERVER_URL = "http://localhost:8888"  # URL de ton Config Server
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

class InvoiceAppConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'invoice_app'

    def ready(self):
        # 1️⃣ Enregistrement sur Eureka
        eureka_client.init(
            eureka_server="http://localhost:8761/eureka",
            app_name="INVOICE_SERVICE",
            instance_port=8000,
            instance_host="localhost"
        )

        # 2️⃣ Charger la configuration depuis le Config Server
        config = load_config()
        print("Configuration récupérée depuis Config Server:", config)

        # 3️⃣ Surcharge des settings Django si nécessaire
        for key, value in config.items():
            setattr(settings, key, value)
