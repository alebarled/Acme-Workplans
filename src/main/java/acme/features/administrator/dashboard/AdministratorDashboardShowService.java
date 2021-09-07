package acme.features.administrator.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.entities.workplans.Workplan;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard>{

	//Internal state -------------------------------------------------
		@Autowired
		protected AdministratorDashboardRepository repository;
		
		@Autowired
		MessageSource messageSource;
		
		
		@Override
		public boolean authorise(final Request<Dashboard> request) {
			assert request !=null;
			return true;
		}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		model.setAttribute("totalworkplans", this.repository.findAllWorkplans().size());
		model.setAttribute("publicworkplans", this.repository.getNumberOfPublicWorkplans());
		model.setAttribute("privateworkplans", this.repository.getNumberOfPrivateWorkplans());
		request.unbind(entity, model, "numberOfPublicTasks", "numberOfPrivateTasks", 
			"numberOfFinishedTasks", "numberOfNonFinishedTasks", 
			"avarageWorkloads", "minimumWorkloads", "maximumWorkloads", "deviationWorkload",
			"avarageExecPeriod", "minimumExecPeriod", "maximumExecPeriod", "deviationExecPeriod",
			"numberOfPublicWorkplans", "numberOfPrivateWorkplans", "numberOfFinishedWorkplans", 
			"numberOfNonFinishedWorkplans", "avarageWorkloadsWorkplan", "minimumWorkloadsWorkplan",
			"maximumWorkloadsWorkplan", "deviationWorkloadWorkplan", "avarageExecPeriodWorkplan",
			"minimumExecPeriodWorkplan", "maximumExecPeriodWorkplan", "deviationExecPeriodWorkplan");
		
	}

	//----------------------------------------------------------------------------------------------------------------------------
		public Float datesTransformationForward(final Float date) {
			final double dat = Double.parseDouble(date.toString());
			final int horas = (int) dat;
			final double minutos = (dat - horas) * 100 * 100 / 60;

			return Float.parseFloat(horas + "." + (int) minutos);
		}

		public Float datesTransformationBackward(final Float date) {
			final double dat = Double.parseDouble(date.toString());
			final int horas = (int) dat;
			final double minutos = (dat - horas) * 100 * 60 / 100;

			return Float.parseFloat(horas + "." + (int) minutos);
		}
		
		public static Float round(final float d, final int decimalPlace) {
		    BigDecimal bd = new BigDecimal(Float.toString(d));
		    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
		    return bd.floatValue();
		}
		
		//----------------------------------------------------------------------------------------------------------------------------
		public Integer getNumberOfPublicTasks(final Request<Dashboard> request) {
			assert request!=null;
			
			return this.repository.getNumberOfPublicTasks();
		}

		public Integer getNumberOfPrivateTasks(final Request<Dashboard> request) {
			assert request!=null;
			
			return this.repository.getNumberOfPrivateTasks();
		}

		
		//----------------------------------------------------------------------------------------------------------------------------
		public Integer getNumberOfFinishedTasks(final Request<Dashboard> request) {
			assert request!=null;

			final ZoneId defaultZoneId = ZoneId.systemDefault();
			
			final LocalDate fechauno = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
			final Date date = Date.from(fechauno.atStartOfDay(defaultZoneId).toInstant());
			
			return this.repository.getNumberOfFinishedTasks(date);
		}

		public Integer getNumberOfNonFinishedTasks(final Request<Dashboard> request) {
			assert request!=null;

			final ZoneId defaultZoneId = ZoneId.systemDefault();
			
			final LocalDate fechauno = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
			final Date date = Date.from(fechauno.atStartOfDay(defaultZoneId).toInstant());
			
			return this.repository.getNumberOfNonFinishedTasks(date);
		}

		
		//----------------------------------------------------------------------------------------------------------------------------
		public Float getAvarageWorkloads(final Request<Dashboard> request) {
			assert request!=null;
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			Float total = 0f;
			
			final List<Task> lsT = this.repository.findAllTasks().stream().collect(Collectors.toList());
			for (int i = 0; i < lsT.size(); i++) {			
				final Float horasMin = this.datesTransformationForward(lsT.get(i).getWorkload());
				
				total += horasMin;
			}
			
			final Float avg = total / lsT.size();
			return AdministratorDashboardShowService.round(this.datesTransformationBackward(avg),2);
		}

		public Float getMinimumWorkloads(final Request<Dashboard> request) {
			assert request!=null;
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			return this.repository.getMinimumWorkloads();
			
		}

		public Float getMaximumWorkloads(final Request<Dashboard> request) {
			assert request!=null;
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			return this.repository.getMaximumWorkloads();
			
		}

		public Float getDeviationWorkloads(final Request<Dashboard> request) {
			assert request!=null;
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			final Float average = this.getAvarageWorkloads(request);
			
			Float res =  0f;
			
			final List<Task> lsT = this.repository.findAllTasks().stream().collect(Collectors.toList());
			for (int i = 0; i < lsT.size(); i++) {			
				final Float diff = this.datesTransformationForward(lsT.get(i).getWorkload());
				
				final Float individualDeviation = Math.abs(diff-average)*Math.abs(diff-average);
				res+=individualDeviation;
			}
			res = res/lsT.size();
			final double sqrt = Math.sqrt(Double.parseDouble(res.toString()));
			final Float final1 = Float.parseFloat(sqrt + "");
			final Float fin = this.datesTransformationBackward(final1);
			return AdministratorDashboardShowService.round(fin,2);
		}
		
			
		
		

		
		//----------------------------------------------------------------------------------------------------------------------------
		public Float getAvarageExecPeriod(final Request<Dashboard> request) {
			assert request!=null;
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			Float total =  0f;
			
			final List<Task> lsT = this.repository.findAllTasks().stream().collect(Collectors.toList());
			for (int i = 0; i < lsT.size(); i++) {
				final Float horasMin = this.datesTransformationForward(lsT.get(i).getPeriod());
				
				total += horasMin;
			}
			

			final Float avg = total/lsT.size();
			return AdministratorDashboardShowService.round(this.datesTransformationBackward(avg),2);
		}

		public Float getMinimumExecPeriod(final Request<Dashboard> request) {
			assert request!=null;
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			Float min =  1000000000000f;
			
			final List<Task> lsT = this.repository.findAllTasks().stream().collect(Collectors.toList());
			for (int i = 0; i < lsT.size(); i++) {
				final Float diff = lsT.get(i).getPeriod();
				if (diff <= min) {
					min = diff;
				}
			}
				
			return AdministratorDashboardShowService.round(min,2);
			
		}

		public Float getMaximumExecPeriod(final Request<Dashboard> request) {
			assert request!=null;
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			Float max =  0f;
			
			final List<Task> lsT = this.repository.findAllTasks().stream().collect(Collectors.toList());
			for (int i = 0; i < lsT.size(); i++) {
				final Float diff = lsT.get(i).getPeriod();
				if (diff >= max) {
					max = diff;
				}
			}
			
			return AdministratorDashboardShowService.round(max,2);
			
			
		}

		public Float getDeviationExecPeriod(final Request<Dashboard> request) {
			if(this.repository.getNumberOfPrivateTasks() + this.repository.getNumberOfPublicTasks() == 0) return null;
			
			final Float average = this.getAvarageExecPeriod(request);
			
			Float res =  0f;

			final List<Task> lsT = this.repository.findAllTasks().stream().collect(Collectors.toList());
			for (int i = 0; i < lsT.size(); i++) {
				final Float diff = this.datesTransformationForward(lsT.get(i).getPeriod());
				
				final Float individualDeviation = Math.abs(diff-average)*Math.abs(diff-average);
				res+=individualDeviation;
			}
			res = res/lsT.size();
			final double sqrt = Math.sqrt(Double.parseDouble(res.toString()));
			final Float final1 = Float.parseFloat(sqrt + "");
			final Float fin = this.datesTransformationBackward(final1);
			return AdministratorDashboardShowService.round(fin,2);
		}
		
		
		//----- workloads functions -------------------------------------------------------------------------
		
		
		public Integer getNumberOfPublicWorkplans(final Request<Dashboard> request) {
			assert request!=null;
			
			return this.repository.getNumberOfPublicWorkplans();
		}

		public Integer getNumberOfPrivateWorkplans(final Request<Dashboard> request) {
			assert request!=null;
			
			return this.repository.getNumberOfPrivateWorkplans();
		}
		
		
		//----------------------------------------------------------------------------------------------------------------------------
			public Integer getNumberOfFinishedWorkplans(final Request<Dashboard> request) {
				assert request!=null;
				final ZoneId defaultZoneId = ZoneId.systemDefault();
					
				final LocalDate fechauno = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
				final Date date = Date.from(fechauno.atStartOfDay(defaultZoneId).toInstant());
					
				return this.repository.getNumberOfFinishedWorkplans(date);
			}

			public Integer getNumberOfNonFinishedWorkplans(final Request<Dashboard> request) {
				assert request!=null;

				final ZoneId defaultZoneId = ZoneId.systemDefault();
					
				final LocalDate fechauno = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
				final Date date = Date.from(fechauno.atStartOfDay(defaultZoneId).toInstant());
					
				return this.repository.getNumberOfNonFinishedWorkplans(date);
			}
			
			
			//----------------------------------------------------------------------------------------------------------------------------
			public Float getAvarageWorkloadsWorkplan(final Request<Dashboard> request) {
				assert request!=null;
				if(this.repository.getNumberOfPrivateWorkplans() + this.repository.getNumberOfPublicWorkplans() == 0) return null;
				
				Float total = 0f;
				
				final List<Workplan> lsT = this.repository.findAllWorkplans().stream().collect(Collectors.toList());
				for (int i = 0; i < lsT.size(); i++) {			
					final Float horasMin = this.datesTransformationForward((float)(double)lsT.get(i).getWorkload());
					
					total += horasMin;
				}
				
				final Float avg = total / lsT.size();
				return AdministratorDashboardShowService.round(this.datesTransformationBackward(avg),2);
			}

			public Float getMinimumWorkloadsWorkplan(final Request<Dashboard> request) {
				assert request!=null;
				
				return (float)(double)this.repository.findAllWorkplans().stream().map(Workplan::getWorkload).min(Comparator.naturalOrder()).orElse(null);
				
			}

			public Float getMaximumWorkloadsWorkplan(final Request<Dashboard> request) {
				assert request!=null;
				return (float)(double)this.repository.findAllWorkplans().stream().map(Workplan::getWorkload).max(Comparator.naturalOrder()).orElse(null);

				
			}

			public Float getDeviationWorkloadsWorkplan(final Request<Dashboard> request) {
				assert request!=null;
				if(this.repository.getNumberOfPrivateWorkplans() + this.repository.getNumberOfPublicWorkplans() == 0) return null;
				
				final Float average = this.getAvarageWorkloadsWorkplan(request);
				
				Float res =  0f;
				
				final List<Workplan> lsT = this.repository.findAllWorkplans().stream().collect(Collectors.toList());
				for (int i = 0; i < lsT.size(); i++) {			
					final Float diff = this.datesTransformationForward((float)(double)lsT.get(i).getWorkload());
					
					final Float individualDeviation = Math.abs(diff-average)*Math.abs(diff-average);
					res+=individualDeviation;
				}
				res = res/lsT.size();
				final double sqrt = Math.sqrt(Double.parseDouble(res.toString()));
				final Float final1 = Float.parseFloat(sqrt + "");
				final Float fin = this.datesTransformationBackward(final1);
				return AdministratorDashboardShowService.round(fin,2);
			}
			

			
			//----------------------------------------------------------------------------------------------------------------------------
			public Float getAvarageExecPeriodWorkplan(final Request<Dashboard> request) {
				assert request!=null;
				if(this.repository.getNumberOfPrivateWorkplans() + this.repository.getNumberOfPublicWorkplans() == 0) return null;
				
				Float total =  0f;
				
				final List<Workplan> lsT = this.repository.findAllWorkplans().stream().collect(Collectors.toList());
				for (int i = 0; i < lsT.size(); i++) {
					final Float horasMin = this.datesTransformationForward(lsT.get(i).getPeriod());
					
					total += horasMin;
				}
				

				final Float avg = total/lsT.size();
				return AdministratorDashboardShowService.round(this.datesTransformationBackward(avg),2);
			}

			public Float getMinimumExecPeriodWorkplan(final Request<Dashboard> request) {
				assert request!=null;
				if(this.repository.getNumberOfPrivateWorkplans() + this.repository.getNumberOfPublicWorkplans() == 0) return null;
				
				Float min =  1000000000000f;
				
				final List<Workplan> lsT = this.repository.findAllWorkplans().stream().collect(Collectors.toList());
				for (int i = 0; i < lsT.size(); i++) {
					final Float diff = lsT.get(i).getPeriod();
					if (diff <= min) {
						min = diff;
					}
				}
					
				return AdministratorDashboardShowService.round(min,2);
				
			}

			public Float getMaximumExecPeriodWorkplan(final Request<Dashboard> request) {
				assert request!=null;
				if(this.repository.getNumberOfPrivateWorkplans() + this.repository.getNumberOfPublicWorkplans() == 0) return null;
				
				Float max =  0f;
				
				final List<Workplan> lsT = this.repository.findAllWorkplans().stream().collect(Collectors.toList());
				for (int i = 0; i < lsT.size(); i++) {
					final Float diff = lsT.get(i).getPeriod();
					if (diff >= max) {
						max = diff;
					}
				}
				
				return AdministratorDashboardShowService.round(max,2);
				
				
			}

			public Float getDeviationExecPeriodWorkplan(final Request<Dashboard> request) {
				if(this.repository.getNumberOfPrivateWorkplans() + this.repository.getNumberOfPublicWorkplans() == 0) return null;
				
				final Float average = this.getAvarageExecPeriodWorkplan(request);
				
				Float res =  0f;

				final List<Workplan> lsT = this.repository.findAllWorkplans().stream().collect(Collectors.toList());
				for (int i = 0; i < lsT.size(); i++) {
					final Float diff = this.datesTransformationForward(lsT.get(i).getPeriod());
					
					final Float individualDeviation = Math.abs(diff-average)*Math.abs(diff-average);
					res+=individualDeviation;
				}
				res = res/lsT.size();
				final double sqrt = Math.sqrt(Double.parseDouble(res.toString()));
				final Float final1 = Float.parseFloat(sqrt + "");
				final Float fin = this.datesTransformationBackward(final1);
				return AdministratorDashboardShowService.round(fin,2);
			}
			

		

		@Override
		public Dashboard findOne(final Request<Dashboard> request) {
			assert request!=null;
			
			final Dashboard result = new Dashboard();
			
			result.setNumberOfPublicTasks(this.getNumberOfPublicTasks(request) + " " + this.messageSource.getMessage("default.dashboard.task", null, LocaleContextHolder.getLocale()));
			result.setNumberOfPrivateTasks(this.getNumberOfPrivateTasks(request) + " " + this.messageSource.getMessage("default.dashboard.task", null, LocaleContextHolder.getLocale()));
			
			result.setNumberOfNonFinishedTasks(this.getNumberOfNonFinishedTasks(request) + " " + this.messageSource.getMessage("default.dashboard.task", null, LocaleContextHolder.getLocale()));
			result.setNumberOfFinishedTasks(this.getNumberOfFinishedTasks(request) + " " + this.messageSource.getMessage("default.dashboard.task", null, LocaleContextHolder.getLocale()));
			
			result.setMinimumWorkloads(this.getMinimumWorkloads(request) !=null?this.getMinimumWorkloads(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setMaximumWorkloads(this.getMaximumWorkloads(request) !=null?this.getMaximumWorkloads(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setAvarageWorkloads(this.getAvarageWorkloads(request) !=null?this.getAvarageWorkloads(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setDeviationWorkload(this.getDeviationWorkloads(request) !=null?this.getDeviationWorkloads(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			
			result.setMinimumExecPeriod(this.getMinimumExecPeriod(request) !=null?this.getMinimumExecPeriod(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setMaximumExecPeriod(this.getMaximumExecPeriod(request) !=null?this.getMaximumExecPeriod(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setAvarageExecPeriod(this.getAvarageExecPeriod(request) !=null?this.getAvarageExecPeriod(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setDeviationExecPeriod(this.getDeviationExecPeriod(request) !=null?this.getDeviationExecPeriod(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			
			result.setNumberOfPublicWorkplans(this.getNumberOfPublicWorkplans(request) + " " + this.messageSource.getMessage("default.dashboard.workplan", null, LocaleContextHolder.getLocale()));
			result.setNumberOfPrivateWorkplans(this.getNumberOfPrivateWorkplans(request) + " " + this.messageSource.getMessage("default.dashboard.workplan", null, LocaleContextHolder.getLocale()));
			
			result.setNumberOfNonFinishedWorkplans(this.getNumberOfNonFinishedWorkplans(request) + " " + this.messageSource.getMessage("default.dashboard.workplan", null, LocaleContextHolder.getLocale()));
			result.setNumberOfFinishedWorkplans(this.getNumberOfFinishedWorkplans(request) + " " + this.messageSource.getMessage("default.dashboard.workplan", null, LocaleContextHolder.getLocale()));
			
			result.setMinimumWorkloadsWorkplan(this.getMinimumWorkloadsWorkplan(request) !=null?this.getMinimumWorkloadsWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setMaximumWorkloadsWorkplan(this.getMaximumWorkloadsWorkplan(request) !=null?this.getMaximumWorkloadsWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setAvarageWorkloadsWorkplan(this.getAvarageWorkloadsWorkplan(request) !=null?this.getAvarageWorkloadsWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setDeviationWorkloadWorkplan(this.getDeviationWorkloadsWorkplan(request) !=null?this.getDeviationWorkloadsWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			
			result.setMinimumExecPeriodWorkplan(this.getMinimumExecPeriodWorkplan(request) !=null?this.getMinimumExecPeriodWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setMaximumExecPeriodWorkplan(this.getMaximumExecPeriodWorkplan(request) !=null?this.getMaximumExecPeriodWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setAvarageExecPeriodWorkplan(this.getAvarageExecPeriodWorkplan(request) !=null?this.getAvarageExecPeriodWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			result.setDeviationExecPeriodWorkplan(this.getDeviationExecPeriodWorkplan(request) !=null?this.getDeviationExecPeriodWorkplan(request) + " " + this.messageSource.getMessage("default.dashboard.workloadPeriod", null, LocaleContextHolder.getLocale()):"-");
			
			return result;
		}

}
