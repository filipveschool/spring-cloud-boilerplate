@Component
@RequiredArgsConstructor
public class VisitsServiceClient {

    private final RestTemplate loadBalancedRestTemplate;

    public Map<Integer, List<VisitDetails>> getVisitsForPets(final List<Integer> petIds, final int ownerId) {
        //TODO:  expose batch interface in Visit Service
        final ParameterizedTypeReference<List<VisitDetails>> responseType = new ParameterizedTypeReference<List<VisitDetails>>() {
        };
        return petIds.parallelStream()
                .flatMap(petId -> loadBalancedRestTemplate.exchange("http://visits-service/owners/{ownerId}/pets/{petId}/visits", HttpMethod.GET, null,
                        responseType, ownerId, petId).getBody().stream())
                .collect(groupingBy(VisitDetails::getPetId));
    }
}