# SafetyNet Alerts

SafetyNet Alerts est une application de gestion d'alertes d'urgence qui vise à faciliter la communication rapide et efficace des informations critiques aux services d'urgence.

## Présentation

Ce projet met en œuvre le back-end de l'application SafetyNet Alerts en utilisant la stack technique suivante :

- Spring Boot pour la gestion de l'application
- Maven pour la gestion des dépendances
- JUnit pour les tests unitaires
- JaCoCo pour la mesure de la couverture de code
- Log4j pour la gestion des logs

## Structure du Projet

Le projet est organisé selon une architecture hexagonale et se compose des fonctionnalités suivantes : la gestion des personnes, des casernes de pompiers et des dossiers médicaux.

## Installation

1. Clonez ce référentiel sur votre machine locale.
2. Assurez-vous d'avoir Java 8 ou une version ultérieure installée.
3. Exécutez la commande `mvn install` pour installer toutes les dépendances.

## Utilisation

Pour lancer l'application, exécutez la classe principale `SafetyNetAlertsApplication` et accédez aux différents endpoints via un navigateur ou un client REST tel que Postman.

## Tests

Pour exécuter les tests, utilisez la commande `mvn test`. La couverture de code peut être consultée dans le rapport généré par JaCoCo après l'exécution des tests.


## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.
