export interface User {
  id: number;
  username: string;
}

export interface Invoice {
  id: number;
  user: User;          // maintenant c’est un objet User, pas un number
  booking_id: number;
  amount: number;
  status: 'UNPAID' | 'PAID' | 'CANCELLED';
  issued_date: string; // ou Date, mais string plus simple pour l’affichage
  paid_date?: string | null;
  pdf_file?: string | null;
}
