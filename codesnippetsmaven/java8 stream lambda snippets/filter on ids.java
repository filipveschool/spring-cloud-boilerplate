public class OwnerDetails {

    private final int id;

    private final String firstName;

    private final String lastName;

    private final String address;

    private final String city;

    private final String telephone;

    private final List<PetDetails> pets = new ArrayList<>();

    @JsonIgnore
    public List<Integer> getPetIds() {
        return pets.stream()
                .map(PetDetails::getId)
                .collect(toList());
    }
}