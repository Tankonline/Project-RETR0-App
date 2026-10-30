FROM golang:1.23 AS build
WORKDIR /src
COPY go.mod ./
COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -trimpath -ldflags='-s -w' -o /out/project-retr0-storage ./cmd/node

FROM gcr.io/distroless/static-debian12:nonroot
COPY --from=build /out/project-retr0-storage /project-retr0-storage
VOLUME ["/var/lib/project-retr0-storage"]
EXPOSE 8788
ENTRYPOINT ["/project-retr0-storage", "-storage-root", "/var/lib/project-retr0-storage", "-listen", "0.0.0.0:8788"]
