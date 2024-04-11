import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbCountry } from '../cb-country.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-country-detail',
  templateUrl: './cb-country-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbCountryDetailComponent {
  cbCountry = input<ICbCountry | null>(null);

  previousState(): void {
    window.history.back();
  }
}
