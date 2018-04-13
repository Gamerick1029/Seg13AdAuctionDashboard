set @row:=-1;
set @nth:=1000;
SELECT [VAR]_impressions.*
FROM
    [VAR]_impressions
    INNER JOIN
    (
        SELECT Date
        FROM
            (
                SELECT @row:=@row+1 AS rownum, Date 
                FROM
                    (
                        SELECT Date FROM [VAR]_impressions ORDER BY Date
                    ) AS sorted
            ) as ranked
        WHERE rownum % @nth = 0
    ) AS subset
        ON subset.Date = [VAR]_impressions.Date;
