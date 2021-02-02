package org.eclipse.jetty.demos;

import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;

public class ErrorOnlyRequestLog extends CustomRequestLog
{
    public ErrorOnlyRequestLog(Writer writer, String formatString)
    {
        super(writer, formatString);
    }

    public ErrorOnlyRequestLog(String file)
    {
        super(file);
    }

    public ErrorOnlyRequestLog(String file, String format)
    {
        super(file, format);
    }

    @Override
    public void log(Request request, Response response)
    {
        // Get the response status actually sent (as you cannot rely on
        // the Response.getStatus() at this point in time, which can change
        // due to a number of factors, including error handling, dispatch
        // completion, recycling, etc)
        int committedStatus = response.getCommittedMetaData().getStatus();

        // only interested in error cases - bad request & server errors
        if ((committedStatus >= 500) || (committedStatus == 400))
        {
            super.log(request, response);
        }
        else
        {
            System.err.println("### Ignored request (response.committed.status=" + committedStatus + "): " + request);
        }
    }
}
