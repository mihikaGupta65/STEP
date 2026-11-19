
import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface ScoringRule {
    double calculateScore(double idea, double execution, double presentation);
}

class InnovationScoring implements ScoringRule {
    public double calculateScore(double idea, double execution, double presentation) {
        return idea * 0.5 + execution * 0.3 + presentation * 0.2;
    }
}

class OpenScoring implements ScoringRule {
    public double calculateScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Track {
    private String trackName;
    private ScoringRule scoringRule;

    public Track(String trackName, ScoringRule scoringRule) {
        this.trackName = trackName;
        this.scoringRule = scoringRule;
    }

    public String getTrackName() {
        return trackName;
    }

    public double calculateScore(double idea, double execution, double presentation) {
        return scoringRule.calculateScore(idea, execution, presentation);
    }
}

class Team {
    private String teamName;
    private List<Student> members;
    private Track track;
    private Project project;

    public Team(String teamName, List<Student> members, Track track) {
        this.teamName = teamName;
        this.members = new ArrayList<>(members);
        this.track = track;
    }

    public String getTeamName() {
        return teamName;
    }

    public Track getTrack() {
        return track;
    }

    public List<Student> getMembers() {
        return members;
    }

    public Project getProject() {
        return project;
    }

    public boolean submitProject(Project project) {
        if (this.project != null) {
            return false;
        }

        this.project = project;
        return true;
    }
}

class Project {
    private String projectName;
    private Team team;
    private Score score;

    public Project(String projectName, Team team) {
        this.projectName = projectName;
        this.team = team;
    }

    public String getProjectName() {
        return projectName;
    }

    public Team getTeam() {
        return team;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}

class Score {
    private double idea;
    private double execution;
    private double presentation;
    private double finalScore;

    public Score(double idea, double execution,
                 double presentation, double finalScore) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
        this.finalScore = finalScore;
    }

    public double getIdea() {
        return idea;
    }

    public double getFinalScore() {
        return finalScore;
    }
}

class Judge {
    private String judgeName;

    public Judge(String judgeName) {
        this.judgeName = judgeName;
    }

    public void scoreProject(Project project, Hackathon hackathon,
                             double idea, double execution,
                             double presentation) {

        if (hackathon.isPublished()) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }

        if (hackathon.getState() != HackathonState.JUDGING) {
            System.out.println("Scoring is not open yet.");
            return;
        }

        if (project.getScore() != null) {
            System.out.println("Project has already been scored.");
            return;
        }

        if (idea < 0 || idea > 10 || execution < 0 || execution > 10
                || presentation < 0 || presentation > 10) {
            System.out.println("Ratings must be between 0 and 10.");
            return;
        }

        double finalScore = project.getTeam().getTrack()
                .calculateScore(idea, execution, presentation);

        project.setScore(new Score(idea, execution, presentation, finalScore));

        System.out.println("Score recorded for '" + project.getProjectName() + "'.");
        System.out.printf("Final score: %.2f%n", finalScore);
    }
}

enum HackathonState {
    OPEN, JUDGING, PUBLISHED
}

class Hackathon {
    private String hackathonName;
    private List<Team> teams;
    private List<Student> registeredStudents;
    private HackathonState state;

    public Hackathon(String hackathonName) {
        this.hackathonName = hackathonName;
        teams = new ArrayList<>();
        registeredStudents = new ArrayList<>();
        state = HackathonState.OPEN;
    }

    public HackathonState getState() {
        return state;
    }

    public boolean isPublished() {
        return state == HackathonState.PUBLISHED;
    }

    public void registerTeam(Team team) {
        if (state != HackathonState.OPEN) {
            System.out.println("Registration is closed.");
            return;
        }

        List<Student> members = team.getMembers();

        if (members.size() < 2 || members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return;
        }

        for (Student student : members) {
            if (registeredStudents.contains(student)) {
                System.out.println("Registration failed: "
                        + student.getName() + " already belongs to another team.");
                return;
            }
        }

        teams.add(team);
        registeredStudents.addAll(members);

        System.out.println("Team " + team.getTeamName()
                + " registered (" + members.size() + " members, "
                + team.getTrack().getTrackName() + " track).");
    }

    public void submitProject(Team team, Project project) {
        if (state != HackathonState.OPEN) {
            System.out.println("Project submission is closed.");
            return;
        }

        if (!teams.contains(team)) {
            System.out.println("Team is not registered.");
            return;
        }

        if (team.submitProject(project)) {
            System.out.println("Project '" + project.getProjectName()
                    + "' submitted by " + team.getTeamName() + ".");
        } else {
            System.out.println("Team can submit only one project.");
        }
    }

    public void startJudging() {
        if (state == HackathonState.OPEN) {
            state = HackathonState.JUDGING;
        }
    }

    public void publishResults() {
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Results have already been published.");
            return;
        }

        state = HackathonState.PUBLISHED;
        System.out.println("Results published.");
    }
}

public class Pb1 {
    public static void main(String[] args) {

        Hackathon hackathon = new Hackathon("Code Sprint");

        Track innovation = new Track("Innovation", new InnovationScoring());
        Track open = new Track("Open", new OpenScoring());

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        List<Student> members1 = new ArrayList<>();
        members1.add(asha);
        members1.add(ravi);
        members1.add(neha);

        Team byteBusters = new Team("ByteBusters", members1, innovation);
        hackathon.registerTeam(byteBusters);

        List<Student> members2 = new ArrayList<>();
        members2.add(kiran);

        Team soloCoder = new Team("SoloCoder", members2, open);
        hackathon.registerTeam(soloCoder);

        Project project = new Project("SmartAttend", byteBusters);
        hackathon.submitProject(byteBusters, project);

        hackathon.startJudging();

        Judge judge = new Judge("Judge 1");
        judge.scoreProject(project, hackathon, 8, 7, 9);

        hackathon.publishResults();

        judge.scoreProject(project, hackathon, 10, 7, 9);
    }
}

