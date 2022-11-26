package team017.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team017.member.entity.Client;
import team017.member.repository.ClientRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
	private final ClientRepository clientRepository;

	/* 존재하는 소비자인지 확인 + 소비자 정보 리턴 */
	public Client findVerifiedClient(long clientId) {
		Optional<Client> optionalClient = clientRepository.findById(clientId);
		Client findClient = optionalClient.orElseThrow(() -> new RuntimeException("Client Not Found"));
		return findClient;
	}

	/* 소비자 조회 */
	@Transactional(readOnly = true)
	public Client findClient(long clientId) {
		Client client = findVerifiedClient(clientId);

		return client;
	}

	/* 소비자 정보 수정 */
	public Client updateClient(long clientId, Client client) {
		Client findClient = findVerifiedClient(clientId);

		return clientRepository.save(findClient);
	}

}
