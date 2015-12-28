# Donor Tools Java Client

donor-tools-api-java provides a Java client for the Donor Tools (donortools.com) API

## Usage

    import org.threeriverdev.donortools.*;

    ...

    DonorToolsClient client = new DonorToolsClient("https://[USERNAME].donortools.com", "username", "password");

    // create Persona
    Persona persona = new Persona();
    ... (set fields as necessary)
    client.create(persona);

    // create Donation
    Donation donation = new Donation();
    ... (set fields as necessary)
    client.create(donation);

    // list all Personas
    List<Persona> personas = client.listPersonas();

## TODOs

These are on my radar (and will likely be needed soon).  However, pull requests are *appreciated*!

1. Search for personas
2. Search for donations

## License

Licensed under the GNU LESSER GENERAL PUBLIC LICENSE (LGPL) V3.  See lgpl-3.0.txt for more information.