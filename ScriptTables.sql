Create table ligue (
  id_ligue int PRIMARY key auto_increment,
  nom_ligue vanchar(100) not null,
);


Create table employe (
  id_employe int primary key auto_increment,
  nom_employe varchar(100) not null,
  prenom_employe varchar(100),
  mail_employe varchar(100),
  mdp_employe varchar(100),
);


