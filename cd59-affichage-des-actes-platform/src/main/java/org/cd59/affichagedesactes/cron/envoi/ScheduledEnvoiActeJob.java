package org.cd59.affichagedesactes.cron.envoi;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.schedule.AbstractScheduledLockedJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

public class ScheduledEnvoiActeJob extends AbstractScheduledLockedJob implements StatefulJob {
    @Override
    public void executeJob(JobExecutionContext jobExecutionContext) {
        JobDataMap jobData = jobExecutionContext.getJobDetail().getJobDataMap();

        // Extract the Job executer to use
        Object executerObj = jobData.get("jobExecuter");
        if (!(executerObj instanceof ScheduledEnvoiActeJobExecuter)) {
            throw new AlfrescoRuntimeException(
                    "ScheduledJob data must contain valid 'Executer' reference");
        }

        final ScheduledEnvoiActeJobExecuter jobExecuter = (ScheduledEnvoiActeJobExecuter) executerObj;

        AuthenticationUtil.runAs(() -> {
            jobExecuter.execute();
            return null;
        }, AuthenticationUtil.getSystemUserName());
    }
}
