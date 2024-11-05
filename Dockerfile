# Usa la imagen oficial de EMQX
FROM emqx/emqx:latest

# Exponer el puerto 1883 para conexiones MQTT y 8081 para el Dashboard HTTP
EXPOSE 1883 8081

# Inicia el servidor
CMD ["emqx"]
