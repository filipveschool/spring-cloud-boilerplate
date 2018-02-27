@Component
@RequiredArgsConstructor
public class CustomersServiceClient {

    private final RestTemplate loadBalancedRestTemplate;

    public OwnerDetails getOwner(final int ownerId) {
        return loadBalancedRestTemplate.getForObject("http://customers-service/owners/{ownerId}", OwnerDetails.class, ownerId);
    }
}