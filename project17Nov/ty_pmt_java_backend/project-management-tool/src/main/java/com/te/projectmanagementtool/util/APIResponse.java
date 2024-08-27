package com.te.projectmanagementtool.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse implements Serializable{

	private Object data;

	private boolean error;

	private String message;
}
