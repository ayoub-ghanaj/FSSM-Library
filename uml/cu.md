## cas d'utilisation
```mermaid
graph LR
  Administrator{{"👤 Administrator"}} --> Utilisateur{{"👤 Utilisateur"}}
  Utilisateur --> NouvelEmprunt["Nouvel Emprunt"]
  NouvelEmprunt --> |include| Matériel["Sélectionner un matériel"]
  NouvelEmprunt --> |include| Etudiant["Coordonnées de l’étudiant"]
  NouvelEmprunt --> |include| Durée["Durée de l’emprunt"]
  Utilisateur --> Historique["Historique des emprunts"]
  Historique --> |include| EtudiantSelectionner["Sélectionner un étudiant"]
  EtudiantSelectionner -.-> |extend| Etudiant
  Utilisateur --> Retards["Lister les retards"]
  Utilisateur --> Retour["Enregistrer le retour"]
  Retour --> |include| Matériel
```
## classes
```mermaid
classDiagram
    class Etudiant {
        - Identifiant : String
        + Nom : String
        + Prénom : String
        - AdresseMail : String
        + Ajouter(object Etudiant)
        + Maj(object Etudiant)
    }
    class Type {
        - Identifiant : Number
        + Libellé : String
    }
    class Matériel {
        - Identifiant : Number
        + Photo : String
        + Nom : String
        # MatérielType : Type
    }
    class Emprunt {
        - Identifiant : Number
        # EtudiantEmprunté : Etudiant
        # MatérielEmprunté : Matériel
        - DateEmprunté : Date
        - DateRetour : Date
        - Durée : Number
    }
    
    Matériel "1..n" -- "1..1" Type : Contient
    Emprunt "0..n" -- "1..1" Matériel : À propos
    Emprunt "1..n" -- "1..1" Etudiant : À propos
```
