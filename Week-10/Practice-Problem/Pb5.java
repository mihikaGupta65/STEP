
interface Reservable {
    void reserve(String memberId);
}

interface Downloadable {
    void download();
}

class Resource {
    protected String id;
    protected String name;

    public Resource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Book extends Resource implements Reservable {
    public Book(String id, String name) {
        super(id, name);
    }

    public void reserve(String memberId) {
        System.out.println(id + " reserved for " + memberId);
    }
}

class EBook extends Resource implements Downloadable {
    public EBook(String id, String name) {
        super(id, name);
    }

    public void download() {
        System.out.println(id + " downloaded");
    }
}

class Library {
    private Resource[] resources = new Resource[100];
    private int count = 0;

    public void addResource(Resource resource) {
        if (findIndex(resource.getId()) != -1) {
            System.out.println("duplicate rejected");
            return;
        }

        int i = count - 1;

        while (i >= 0 && resources[i].getId().compareTo(resource.getId()) > 0) {
            resources[i + 1] = resources[i];
            i--;
        }

        resources[i + 1] = resource;
        count++;

        System.out.println(resource.getId() + " added");
    }

    public int findIndex(String id) {
        int left = 0;
        int right = count - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int result = resources[mid].getId().compareTo(id);

            if (result == 0) {
                return mid;
            } else if (result < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public void reserve(String id, String memberId) {
        int index = findIndex(id);

        if (index == -1) {
            System.out.println(id + " not found");
            return;
        }

        Resource resource = resources[index];

        if (resource instanceof Reservable) {
            ((Reservable) resource).reserve(memberId);
        } else {
            System.out.println(id + " rejected: reserve unsupported");
        }
    }

    public void download(String id) {
        int index = findIndex(id);

        if (index == -1) {
            System.out.println(id + " not found");
            return;
        }

        Resource resource = resources[index];

        if (resource instanceof Downloadable) {
            ((Downloadable) resource).download();
        } else {
            System.out.println(id + " rejected: download unsupported");
        }
    }

    public void find(String id) {
        int index = findIndex(id);

        if (index == -1) {
            System.out.println(id + " not found");
        } else {
            System.out.println(id + " found at index " + index
                    + " name " + resources[index].getName());
        }
    }
}

public class Pb5 {

    public static void main(String[] args) {
        Library library = new Library();

        library.addResource(new Book("B1", "Java Programming"));
        library.addResource(new Book("B1", "Java Programming"));

        library.addResource(new EBook("E1", "Python Basics"));

        library.reserve("B1", "M1");

        library.download("B1");

        library.download("E1");

        library.find("E1");
    }
}
