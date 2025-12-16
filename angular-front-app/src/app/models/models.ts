export interface Keynote {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  fonction: string;
}

export interface Review {
  id: number;
  date: Date;
  texte: string;
  stars: number;
}

export interface Conference {
  id: number;
  titre: string;
  type: string;
  date: Date;
  duree: number;
  nombreInscrits: number;
  score: number;
  keynote: Keynote; // Peut être null si le service keynote est down
  reviews: Review[];
}
