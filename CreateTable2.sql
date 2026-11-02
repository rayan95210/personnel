create table ligue (
  id_ligue int primary key auto_increment,
  nom_ligue varchar(100) not null,
);


create table employe (
  id_employe int primary key auto_increment,
  nom_employe varchar(100) not null,
  prenom_employe varchar(100) not null,
  mail_employe varchar(100) not null,
  mdp_employe varchar(100) not null,
  date_arrivee_employe varchar(100) not null,
  
