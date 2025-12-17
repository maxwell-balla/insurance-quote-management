# insurance-quote-management

Systeme de gestion de devis d'assurance construit avec l'Architecture Hexagonale et le Domain-Driven Design.

## Contexte

Ce projet est un playground pour explorer les bonnes pratiques de developpement logiciel :
- **Architecture Hexagonale** : Separation stricte entre le domaine metier et les details techniques
- **Domain-Driven Design** : Modelisation du domaine avec des bounded contexts distincts
- **Test-Driven Development** : Tests unitaires avec des fakes, tests de composants avec Cucumber

Le systeme permet a un agent d'assurance de creer des devis pour ses clients. Le tarif est calcule par un service de pricing externe, avec un mecanisme de fallback si le service est indisponible.

## Bounded Contexts

| Module | Description | Base de donnees |
|--------|-------------|-----------------|
| **devis** | Creation et gestion des devis | PostgreSQL |
| **pricing** | Calcul des tarifs | MongoDB |

## Règles de gestion

### Devis (Quote)

**Capital positif** — Le capital assuré doit être strictement positif

**Durée positive** — La durée du contrat doit être strictement positive (en jours)

**Âge du client** — L'âge du client doit être compris entre 18 et 90 ans

**Tarif positif** — Le tarif calculé doit être strictement positif

**Types de produits** — Les produits disponibles sont : `AUTO`, `HEALTH`

**Statuts du devis** — Un devis peut avoir les statuts : `CREATED`, `VALIDATED`, `EXPIRED`, `ERROR`

### Tarification

**Calcul du tarif** — Si le service de pricing est disponible, le tarif calculé par ce service est utilisé

**Tarif par défaut (fallback)** — Si le service de pricing est indisponible, un tarif par défaut est appliqué

## Use Cases

### Create Quote

Permet à un agent d'assurance de créer un devis pour un client.

#### Create Quote Workflow

```java
// 1. Clean Architecture: Request enters through Primary Adapter (Controller)
@PostMapping("/v1/quote")
public ResponseEntity<CreateQuoteResponse> createQuote(@RequestBody @Valid CreateQuoteRequest request)

// 2. Clean Architecture: Request mapped to UseCase Request
CreateQuoteRequest useCaseRequest = QuoteDtoMapper.INSTANCE.mapToCreateQuoteCmd(request);

// 3. Clean Architecture: UseCase orchestrates the business logic
createQuoteUseCase.execute(useCaseRequest);

// 4. Hexagonal: Secondary Port (PricingPort) retrieves tariff from external service
BigDecimal tarif = pricingPort.getTarif(productType, profil);

// 5. DDD: Domain Model (Quote) enforces business rules
Quote quote = Quote.create(customerId, productType, age, capital, duration, tarif);

// 6. Clean Architecture: Secondary Port (QuoteRepositoryPort) abstracts persistence
QuoteSnapshot savedSnapshot = quoteRepoPort.save(quote);
```

### Get Tarif

Permet de récupérer le tarif pour un type de produit et un profil client donné.

#### Get Tarif Workflow

```java
// 1. Clean Architecture: Request enters through Primary Adapter (Controller)
@GetMapping("/v1/pricing")
public ResponseEntity<GetPricingResponse> getTarif(ProductType productType, Integer age)

// 2. Clean Architecture: Request mapped to UseCase Request
GetTarifRequest request = PricingDtoMapper.INSTANCE.mapToGetTarifQuery(productType, age);

// 3. Clean Architecture: UseCase orchestrates the business logic
getTarifUseCase.execute(request);

// 4. DDD: Domain Model (Price) created with business rules
Price price = Price.of(productType, profil);

// 5. Clean Architecture: Secondary Port (PricingRepositoryPort) retrieves tariff
BigDecimal tariff = pricingRepo.getTarif(productType, profil);
```

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

- Java 21
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

# Tests de composants (Cucumber + Testcontainers + WireMock)
./mvnw test -pl starter-devis
```

> **Note** : Les tests dans `starter-devis` sont des tests de composants, pas des tests E2E. Les services externes (ex: Pricing) sont stubbes avec WireMock.

## Documentation API

Specifications OpenAPI :
- `devis/infrastructure-devis/adapter-restapi-spring-devis/src/main/resources/openapi/api.yaml`
- `pricing/infrastructure-pricing/adapter-restapi-pricing-spring/src/main/resources/openapi/api.yaml`

## Stack technique

- Java 21, Spring Boot 4, Spring Framework 7
- PostgreSQL, MongoDB
- Keycloak (authentification)
- Cucumber, Testcontainers, WireMock (tests de composants)
