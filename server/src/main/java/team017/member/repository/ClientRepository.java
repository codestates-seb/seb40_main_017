package team017.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team017.member.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
