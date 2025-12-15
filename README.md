# insurance-quote-management

Systeme de gestion de devis d'assurance construit avec l'Architecture Hexagonale et le Domain-Driven Design.

## Contexte

Ce projet est un playground pour explorer les bonnes pratiques de developpement logiciel :
- **Architecture Hexagonale** : Separation stricte entre le domaine metier et les details techniques
- **Domain-Driven Design** : Modelisation du domaine avec des bounded contexts distincts
- **Test-Driven Development** : Tests unitaires avec des fakes, tests E2E avec Cucumber

Le systeme permet a un agent d'assurance de creer des devis pour ses clients. Le tarif est calcule par un service de pricing externe, avec un mecanisme de fallback si le service est indisponible.

## Bounded Contexts

| Module | Description | Base de donnees |
|--------|-------------|-----------------|
| **devis** | Creation et gestion des devis | PostgreSQL |
| **pricing** | Calcul des tarifs | MongoDB |

## Regles de gestion

### Devis (Quote)
- Le **capital** doit etre strictement positif
- La **duree** doit etre strictement positive (en jours)
- L'**age** du client doit etre compris entre 18 et 120 ans
- Le **tarif** doit etre strictement positif
- Types de produits disponibles : `AUTO`, `HEALTH`
- Statuts possibles : `CREATED`, `VALIDATED`, `EXPIRED`, `ERROR`

### Tarification
- Si le service de pricing est disponible : utilisation du tarif calcule
- Si le service de pricing est indisponible : application d'un tarif par defaut (fallback)

## Architecture

Ce projet suit l'**Architecture Hexagonale (Ports & Adapters)** avec une separation stricte :

```
{module}/
├── application-core/    # Logique domaine pure (sans framework)
├── infrastructure/      # Adaptateurs (API REST, persistence, services externes)
└── starter/             # Point d'entree Spring Boot
```

Les modules core n'ont aucune dependance framework - garanti par Maven Enforcer.

## Prerequis

- Java 25
- Maven 3.9+
- Docker (pour Testcontainers)

## Build & Run

```bash
# Build du module devis
cd devis
./mvnw clean install

# Build du module pricing
cd pricing
./mvnw clean install

# Lancer l'application devis
cd devis/starter-devis
./mvnw spring-boot:run

# Lancer l'application pricing
cd pricing/starter-pricing
./mvnw spring-boot:run
```

## Tests

```bash
# Tests unitaires
./mvnw test

# Tests E2E (Cucumber + Testcontainers)
./mvnw test -pl starter-devis
```

## Documentation API

Specifications OpenAPI :
- `devis/infrastructure-devis/adapter-restapi-spring-devis/src/main/resources/openapi/api.yaml`
- `pricing/infrastructure-pricing/adapter-restapi-pricing-spring/src/main/resources/openapi/api.yaml`

## Stack technique

- Java 25, Spring Boot 4.0
- PostgreSQL, MongoDB
- Keycloak (authentification)
- Cucumber, Testcontainers, WireMock (tests)
