package kz.berekebank.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SessionsListRs {
    private List<SessionRs> sessions;
}
