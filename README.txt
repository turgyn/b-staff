docker build -t komek-backend .

docker run -p 8080:8080 komek-backend

docker run --name my-postgres -e POSTGRES_PASSWORD=turgyn -e POSTGRES_USER=turgyn -d -p 5432:5432 postgres
