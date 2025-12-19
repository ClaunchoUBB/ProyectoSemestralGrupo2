# Usamos Nginx Alpine
FROM nginx:alpine

# Copiamos todos los archivos de frontend al contenedor
COPY . /usr/share/nginx/html

# Exponemos el puerto estándar de Nginx
EXPOSE 80

# Arrancamos Nginx
CMD ["nginx", "-g", "daemon off;"]
