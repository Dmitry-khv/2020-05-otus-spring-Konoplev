package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class JobCommands {

    private final JobLauncher jobLauncher;
    private final Job migrateMongoToJpaJob;

    @ShellMethod(value = "Migrate all products and categories.", key = {"migrate"})
    public String migrateMongoToJpa() throws Exception {
        var execution = jobLauncher.run(migrateMongoToJpaJob, new JobParameters());
        return execution.toString();
    }

}
