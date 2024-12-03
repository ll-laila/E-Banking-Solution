import { ClientComponentsModule } from './ClientComponents.module';

describe('ComponentsModule', () => {
  let componentsModule: ClientComponentsModule;

  beforeEach(() => {
    componentsModule = new ClientComponentsModule();
  });

  it('should create an instance', () => {
    expect(componentsModule).toBeTruthy();
  });
});
