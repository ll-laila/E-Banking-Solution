export interface WalletCrypto {
  id: string; // Identifiant unique du portefeuille
  userId: string; // Identifiant de l'utilisateur
  cryptos: { [key: string]: number }; // Dictionnaire où la clé est le nom de la cryptomonnaie et la valeur est la quantité
}
