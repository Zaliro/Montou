<p align="center">
  <img src="https://i.imgur.com/HRAyxGJ.png" width="800" />
</p>

# Montou*V1*
Implémentation minimaliste d'un **RobotWar** incluant un système de plugins ainsi qu'une notion de persistence *(sauvegarde)* des parties.

**Ce projet se décompose en deux modules** : <i>Game</i> et <i>BasePlugins</i>.

#### &rarr; Exigences

1. Eclipse.
2. JDK &ge; 8.
3. Maven &ge; 3.3.9.

## Démarrer le jeu

:exclamation: Il est indispensable de modifier la valeur **project.properties.jdk** du **Montou/pom.xml** avant toutes choses !

#### Première solution - Utiliser la "LaunchConfiguration".
1. Sous Eclipse &gt; Run &gt; Run Configurations... &gt; Maven Build &gt; LaunchConfiguration.
2. Personnaliser les arguments de lancement à votre guise : **exec.args** &gt; -**pluginsJarPath** -**persistenceDirPath**.
3. Run.

------------

#### Deuxième solution - Lancement en ligne de commande avec Maven.
1. Taper dans la console **Maven** : `mvn exec:java -Dexec.mainClass="com.montou.game.engine.Launcher" -Dexec.args="pluginsJarPath persistenceDirPath"`.
2. Pressez la touche "Entrée".

:warning: <i>Si la partie n'est pas terminée lorsque vous quitter le programme, elle sera automatiquement reprise lors du prochain lancement.</i>

:information_source: <i>Si vous ne souhaitez pas que cette partie reprenne, il vous suffit de supprimer l'ensemble des fichiers se trouvant dans le dossier  **persistenceDirPath**.</i>

#### Troisième solution - Lancement avec JAR.
1. Se positionner (via la console) dans le dossier "Demonstration".
2. Exécuter la ligne de commande suivante : "java -jar Game-0.0.1.jar BasePlugins-0.0.1.jar /Persistence"

## Développer un plugin
Il existe **trois types de plugins** bien distincts à savoir :
##### 1. Les plugins graphiques
*Ils servent à afficher des objets ou des entités sur le plateau de jeu.*
##### 2. Les plugins de gestion de mouvements
*Ils servent à définir un comportement concernant le prochain mouvement qu'un robot va effectuer.*
##### 3. Les plugins de gestion d'attaques
*Ils servent à définir un comportement concernant la prochaine attaque qu'un robot va (ou non) effectuer.*

### Développement d'un plugin graphique
**Annotation à préciser** *(sur votre classe)* : @AGraphic.

**Méthode à implémenter** :
```java
public void draw(GameInformations gameInformations, Graphics g)
```
Vous pouvez donc utiliser l'objet ```Graphics g``` pour dessiner sur le plateau de jeu ce que bon vous semble.<br>
Vous disposez également d'informations concernant la partie grâce à l'objet ```GameInformations gameInformations```.

### Développement d'un plugin de gestion de mouvements
**Annotation à préciser** *(sur votre classe)* : @AMovement(**energyCost** = **n**).

**Méthode à implémenter** :
```java
public Direction move(GameInformations gameInformations, int currentPlayer)
```
Vous devez donc retourner une ```Direction``` qui sera utilisée par le moteur de jeu afin de faire bouger le robot en question *(currentPlayer)*  lors du prochain tour.

### Développement d'un plugin de gestion d'attaques
**Annotation à préciser** *(sur votre classe)* : @AAttack(**energyCost** = **n**).
**Méthode à implémenter** :
```java
public int attack(GameInformations gameInformations) 
```

Vous devez retourner un entier ```int``` qui correspond aux dommages appliqués au robot ennemi.

### Rendre votre plugin persistant
*Tous les types de plugins peuvent implémenter le système de persistence mis à disposition.*

**Attribut à préciser** selon votre type de plugin : @AGraphic(**useCustomData** = **true**), @AMovement(**useCustomData** = **true**) ou @AAttack(**useCustomData** = **true**).

**Méthodes à implémenter** : 
```java
public void onLoad(Object[] objects)
```
Cette méthode est appelée lors de la reprise d'une partie pour que le plugin puisse retrouver l'état dans lequel il a été fermé.
```java
public Object[] onSave()
```
Cette méthode est appelée lors de la fin d'un tour afin de sauvegarder l'état du plugin au cas où la partie s'arrêterait de manière innatendue.

<i>Pour une meilleure compréhension du système de plugins, nous vous conseillons d'étudier le module **BasePlugins**</i>.

## Notre équipe
Noé Mourton-Comte<br/>
Maxime Dien<br/>
Hugo Fourmy<br/>
Robin Balbis<br/>
