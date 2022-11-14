package team017.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team017.member.entity.Client;
import team017.member.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {
	private final ClientRepository clientRepository;

	/* 존재하는 소비자인지 확인 + 소비자 정보 리턴 */
	public Client findVerifiedClient(long clientId) {
		Optional<Client> optionalClient = clientRepository.findById(clientId);
		Client findClient = optionalClient.orElseThrow(() -> new RuntimeException("Client Not Found"));
		return findClient;
	}

	/* 소비자 정보 수정 */
	public Client updateClient(long clientId, Client client) {
		correctClient(clientId, client.getClientId());
		Client findClient = findVerifiedClient(client.getClientId());

		return clientRepository.save(findClient);
	}

	/*url clientId와 dto clientId 일치 판별*/
	public void correctClient(long clientId, long getId) {
		if (clientId != getId) {
			throw new RuntimeException("회원 정보를 확인하세요.");
		}
	}
}
