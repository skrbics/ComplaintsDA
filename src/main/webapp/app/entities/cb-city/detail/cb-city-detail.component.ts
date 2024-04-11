import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbCity } from '../cb-city.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-city-detail',
  templateUrl: './cb-city-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbCityDetailComponent {
  cbCity = input<ICbCity | null>(null);

  previousState(): void {
    window.history.back();
  }
}
